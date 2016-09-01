using System;
using System.Collections.Generic;
using System.Drawing;
using System.Net;
using System.Net.Sockets;
using System.Text;
using System.Windows.Forms;
using kr.co.bcu.propio.util;
using kr.co.bcu.propio.win32api.define;
using kr.co.bcu.propio.win32api.define.model;
using kr.co.bcu.propio.win32api.dll;

namespace SumaPhoneServer
{
    public partial class ServerForm : Form
    {
        //define
        private Define define;

        //커서 위치를 버퍼링 하기위한 변수 선언
        private List<Point> ptList = null;
        private System.Windows.Forms.Timer timer = null;

        //UDP 서버와 관련된 변수 선언
        private bool bRunFlag = false;
        private Socket udpServer = null;
        private System.Threading.Thread udpThread = null;
        private System.Threading.Thread acptThread = null;

        //Log 기록하기 위한 변수 선언
        private delegate void LogTextDelegate(String log); // Cross-Thread 호출을 실행하기 위해사용
        private LogTextDelegate txtLogDelegate;

        public ServerForm()
        {
            InitializeComponent();

            //init define
            define = __BaseSerializer.Read<Define>(@".\define\Define.xml");
            
            //프로그램 종료 이벤트
            Application.ApplicationExit += new EventHandler(Application_ApplicationExit);

            //init cursor location buffer
            ptList = new List<Point>();
            timer = new System.Windows.Forms.Timer();
            timer.Interval = 100;
            timer.Tick += new EventHandler(timer_Tick);
            timer.Enabled = true;

            txtLogDelegate = new LogTextDelegate(txtLog.AppendText);
            
            //init config
            Microsoft.Win32.RegistryKey rkey = Microsoft.Win32.Registry.CurrentUser
                .OpenSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", true);

            object obj = rkey.GetValue("SumaPhone");
            if (obj != null) { chkStartProgram.Checked = true; }
        }
        private void ServerForm_Load(object sender, EventArgs e)
        {
            toNotifyIcon(true);
            acptThread = new System.Threading.Thread(new System.Threading.ThreadStart(this.UDP_AcceptThread));
            acptThread.Start();
        }

        private void btnAccept_Click(object sender, EventArgs e)
        {
            if (acptThread != null)
            {
                acptThread.Start();
            }
            else
            {
                acptThread = new System.Threading.Thread(new System.Threading.ThreadStart(this.UDP_AcceptThread));
                acptThread.Start();
            }
        }
        private void notifyIcon_DoubleClick(object sender, EventArgs e)
        {
            toNotifyIcon(false);
        }

        private void timer_Tick(object sender, EventArgs e)
        {
            timer.Enabled = false;
            for (int i = 0; i < ptList.Count; i++)
            {
                User32dll.SetCursorPos(ptList[0].X, ptList[0].Y);
                ptList.RemoveAt(0);
            }
            timer.Enabled = true;
        }

        //UDP Server Start
        public void UDP_AcceptThread()
        {
            bool isConnected = false;
            while (!isConnected)
            {
                try
                {
                    WriteMessage("Wait for Network Connection");
                    using (var client = new System.Net.WebClient())
                    using (var stream = client.OpenRead("http://www.google.com"))
                    {
                        isConnected = true;
                        UDP_Accept();
                    }
                }
                catch
                {
                    WriteMessage("Network Connection Failed");
                    System.Threading.Thread.Sleep(10000);
                }
            }
        }
        public void UDP_Accept()
        {
            if (bRunFlag)
            {
                WriteMessage("Waiting !");
                return;
            }
            UDP_StartThread();
        }
		public void UDP_StartThread()
		{
			try
			{
                udpThread = new System.Threading.Thread(new System.Threading.ThreadStart(this.UDP_StartListening));
                udpThread.Start();
			}
			catch
			{
                if (udpThread != null) System.Threading.Thread.ResetAbort();
			}
		}
		public void UDP_StartListening()
		{
            udpServer = new Socket(AddressFamily.InterNetwork, SocketType.Dgram, ProtocolType.Udp);

            EndPoint local = new IPEndPoint(IPAddress.Any, int.Parse(txtPort.Text));
            EndPoint senderRemote = new IPEndPoint(IPAddress.None, int.Parse(txtPort.Text));

            udpServer.Bind(local);
            WriteMessage("Server is Waiting for Data");
            bRunFlag = true;
            while (bRunFlag)
			{
				try 
				{
					byte[] msg = new byte[512];

					// This call blocks.
                    int msgSize = udpServer.ReceiveFrom(msg, ref senderRemote);
                    WriteMessage(Encoding.UTF8.GetString(msg, 0, msgSize));
				}
				catch (Exception e) 
				{
                    if (udpServer != null) udpServer.Close();
					Console.WriteLine("Exception : " + e.ToString());
				}
			}
		}

        private void WriteMessage(string value)
        {
            Invoke(txtLogDelegate, "MSG : " + value + "\r\n");
            
            //if (message.Equals("keyup")) { keybdEvent(VK.UP); }
            //else if (message.Equals("keydown")) { keybdEvent(VK.DOWN); }
            //else if (message.Equals("keyleft")) { keybdEvent(VK.LEFT); }
            //else if (message.Equals("keyright")) { keybdEvent(VK.RIGHT); }
            //else if (message.Equals("keyspace")) { keybdEvent(VK.SPACE); }
            //else if (message.Equals("pageup")) { keybdEvent(VK.PRIOR); }
            //else if (message.Equals("pagedown")) { keybdEvent(VK.NEXT); }
            //else if (message.Equals("comma")) { keybdEvent(0xBC); }
            //else if (message.Equals("period")) { keybdEvent(0xBE); }
            if (value.Equals("leftclick")) { mouseEvent(MOUSEEVENTF.LEFTDOWN, MOUSEEVENTF.LEFTUP); }
            else if (value.Equals("rightclick")) { mouseEvent(MOUSEEVENTF.RIGHTDOWN, MOUSEEVENTF.RIGHTUP); }
            else if (value.Equals("wheelclick")) { mouseEvent(MOUSEEVENTF.MIDDLEDOWN, MOUSEEVENTF.MIDDLEUP); }
            else if (value.Equals("mudn")) { mouseStartPoint(); }
            else if (value.IndexOf("mumv") != -1) { mouseMoveEvent(value); }
            else { KeyUpDownEvent(value); }
        }

        public void KeyUpDownEvent(string value)
        {
            User32dll.keybd_event(define.GetValue(value), 0, KEYEVENTF.EXTENDEDKEY, 0);
            User32dll.keybd_event(define.GetValue(value), 0, KEYEVENTF.KEYUP, 0);
        }

        public void mouseEvent(uint down, uint up)
        {
            User32dll.mouse_event(down, 0, 0, 0, 0);
            User32dll.mouse_event(up, 0, 0, 0, 0);
        }

        private Point point = new Point(0, 0);
        public void mouseStartPoint()
        {
            User32dll.GetCursorPos(ref point);
        }
        public void mouseMoveEvent(String msg) 
        {
            string[] ary = msg.Split('/');
            ptList.Add(new Point(point.X + int.Parse(ary[1]), point.Y + int.Parse(ary[2])));
        }

        private void ServerForm_FormClosing(object sender, FormClosingEventArgs e)
        {
            toNotifyIcon(true);
            e.Cancel = true;
        }
        private void Application_ApplicationExit(object sender, EventArgs e)
        {
            if (acptThread != null)
            {
                acptThread.Abort();
            }
            if (udpThread != null)
            {
                bRunFlag = false;
                udpServer.Close();
            }
        }
        private void closeToolStripMenuItem_Click(object sender, EventArgs e)
        {
            Application.Exit();
        }

        private void toNotifyIcon(bool value)
        {
            this.Visible = !value;
            this.ShowInTaskbar = !value;
            this.WindowState = FormWindowState.Normal;
            notifyIcon.Visible = value;
        }

        private void chkStartProgram_CheckedChanged(object sender, EventArgs e)
        {
            setStartRegi();
        }
        private void setStartRegi()
        {
            Microsoft.Win32.RegistryKey rkey = Microsoft.Win32.Registry.CurrentUser
                .OpenSubKey("SOFTWARE\\Microsoft\\Windows\\CurrentVersion\\Run", true);
 
            if (chkStartProgram.Checked)
                rkey.SetValue("SumaPhone", Application.ExecutablePath.ToString());
            else
                rkey.DeleteValue("SumaPhone", false);
        }
    }
}
