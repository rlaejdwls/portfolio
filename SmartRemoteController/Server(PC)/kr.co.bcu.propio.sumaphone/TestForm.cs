using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data;
using System.Drawing;
using System.Linq;
using System.Text;
using System.Windows.Forms;
using System.Net.Sockets;
using System.Net;
using System.IO;

namespace SumaPhoneServer
{
    public partial class TestForm : Form
    {
        private IPAddress serverIP = null;
        private Socket c_scoket = null;

        private NetworkStream ns = null;
        private StreamWriter writer = null;

        //private Thread endTh;
        //private Thread endTh2;


        public TestForm()
        {
            InitializeComponent();
        }

        private void button1_Click(object sender, EventArgs e)
        {
            if (c_scoket == null)
            {
                conncet();
            }
            else
            {
                disconnect();
            }
        }

        private void conncet()
        {
            c_scoket = new Socket(AddressFamily.InterNetwork, SocketType.Stream, ProtocolType.Tcp);
            c_scoket.Connect(new IPEndPoint(IPAddress.Parse("127.0.0.1"), int.Parse("7696")));
            ns = new NetworkStream(c_scoket);
            writer = new StreamWriter(ns);
        }

        private void disconnect()
        {
            writer.WriteLine("CloseSocket");
            writer.Flush();
            writer.Close();
            ns.Close();
            c_scoket.Disconnect(false);
            c_scoket = null;
        }

        private void button2_Click(object sender, EventArgs e)
        {
            SendMessage(textBox1.Text);
        }

        private void SendMessage(string message)
        {
            writer.WriteLine(message);
            writer.Flush();
        }

        private void button3_Click(object sender, EventArgs e)
        {

        }
    }
}
