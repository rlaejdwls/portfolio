using System;
using System.Windows.Forms;
using kr.co.bcu.propio.win32api.dll;
using kr.co.bcu.propio.xbox.lib;
using kr.co.bcu.propio.xbox.lib.handler.model;
using kr.co.bcu.propio.win32api.define;

namespace kr.co.bcu.propio.game.xbox.pad.test
{
    public partial class MainForm : Form
    {
        public MainForm()
        {
            InitializeComponent();
            System.Windows.Forms.Control.CheckForIllegalCrossThreadCalls = false;
        }

        private XBOXPadEvents events;

        private void button1_Click(object sender, EventArgs e)
        {
            
        }

        private void Form1_Load(object sender, EventArgs e)
        {
            events = new XBOXPadEvents();
            events.ButtonEvent += new propio.xbox.lib.handler.XBOXPadButtonEventHandler(controller_ButtonPressed);
            events.StickEvent += new propio.xbox.lib.handler.XBOXPadStickEventHandler(controller_StickEvent);
            events.TriggerEvent += new propio.xbox.lib.handler.XBOXPadTriggerEventHandler(controller_TriggerEvent);
        }

        public void controller_ButtonPressed(XBOXPadButtonEventArgs e)
        {
            if (e.ButtonInfo.A == Microsoft.Xna.Framework.Input.ButtonState.Pressed)
            {
                keybdEvent(0x41);
            }
            if (e.ButtonInfo.B == Microsoft.Xna.Framework.Input.ButtonState.Pressed)
            {
                keybdEvent(0x42);
            }
        }
        public void controller_StickEvent(XBOXPadStickEventArgs e)
        {
            System.Drawing.Point point = new System.Drawing.Point();
            User32dll.GetCursorPos(ref point);
            int x = (int)Math.Truncate(e.StickInfo.Left.X * 20);
            int y = -(int)Math.Truncate(e.StickInfo.Left.Y * 20);

            User32dll.SetCursorPos(point.X + x, point.Y + y);
        }
        private bool click = false;
        public void controller_TriggerEvent(XBOXPadTriggerEventArgs e)
        {
            if (e.TriggersInfo.Left > 0.5)
            {
                click = true;
            }

            if (e.TriggersInfo.Left == 0 && click)
            {
                click = false;
                mouseEvent(MOUSEEVENTF.LEFTDOWN, MOUSEEVENTF.LEFTUP);
            }
        }

        private void Form1_FormClosed(object sender, FormClosedEventArgs e)
        {
            events.IsCallbackThreading = false;
        }

        public void keybdEvent(byte bVk)
        {
            User32dll.keybd_event(bVk, 0, KEYEVENTF.EXTENDEDKEY, 0);
            User32dll.keybd_event(bVk, 0, KEYEVENTF.KEYUP, 0);
        }

        public void mouseEvent(uint down, uint up)
        {
            User32dll.mouse_event(down, 0, 0, 0, 0);
            User32dll.mouse_event(up, 0, 0, 0, 0);
        }
    }
}
