using com.tistory.feelmcsna.controllersupporter.define.model;
using com.tistory.feelmcsna.controllersupporter.keyset.model;
using kr.co.bcu.propio.util;
using kr.co.bcu.propio.util.config;
using kr.co.bcu.propio.util.config.model;
using kr.co.bcu.propio.win32api.define;
using kr.co.bcu.propio.win32api.dll;
using kr.co.bcu.propio.xbox.lib;
using kr.co.bcu.propio.xbox.lib.handler.model;

namespace com.tistory.feelmcsna.controllersupporter
{
    public partial class MainForm : System.Windows.Forms.Form
    {
        public MainForm()
        {
            InitializeComponent();
        }

        private KeySet keySet;
        private Define define;
        private XBOXPadEvents xboxPadEvents;

        private System.Collections.Hashtable isKeyDown = new System.Collections.Hashtable();
        private System.Collections.Hashtable isMouseDown = new System.Collections.Hashtable();
        
        private void MainForm_Load(object sender, System.EventArgs e)
        {
            Initialize();
        }
        private void MainForm_FormClosed(object sender, System.Windows.Forms.FormClosedEventArgs e)
        {
            if (xboxPadEvents != null) { xboxPadEvents.IsCallbackThreading = false; }
        }

        public void Initialize()
        {
            ConfigModel config = __BaseConfig.getConfig();

            xboxPadEvents = new XBOXPadEvents();
            xboxPadEvents.ButtonEvent += new kr.co.bcu.propio.xbox.lib.handler.XBOXPadButtonEventHandler(XBOXPad_ButtonEvent);
            xboxPadEvents.DPadEvent += new kr.co.bcu.propio.xbox.lib.handler.XBOXPadDPadEventHandler(XBOXPad_DPadEvent);
            xboxPadEvents.TriggerEvent += new kr.co.bcu.propio.xbox.lib.handler.XBOXPadTriggerEventHandler(XBOXPad_TriggerEvent);
            xboxPadEvents.StickEvent += new kr.co.bcu.propio.xbox.lib.handler.XBOXPadStickEventHandler(XBOXPad_StickEvent);

            keySet = __BaseSerializer.Read<KeySet>(config.ToString("LastOpenKeysetPath"));
            define = __BaseSerializer.Read<Define>(@".\define\Define.xml");

            isMouseDown.Add("MouseLeftClick", false);
            isMouseDown.Add("MouseMiddleClick", false);
            isMouseDown.Add("MouseRightClick", false);

            if (keySet == null)
            {
                keySet = new KeySet();
                keySet.PlayerList.Add(new PlayerList());
            }

            PrintBtnValue();

            //!Test
            BtnSticksLeft.Text = keySet.PlayerList[0].StickSet.Left;
            //this.BtnLeftStick;
        }

        /**
         * XBOX Pad 이벤트 구간
         */
        private void XBOXPad_ButtonEvent(XBOXPadButtonEventArgs e)
        {
            XBOXPadDynamicButtonEvent(e.ButtonInfo.GetType().GetMethods(), e.ButtonInfo, keySet.PlayerList[0].ButtonSet);
        }
        private void XBOXPad_DPadEvent(XBOXPadDPadEventArgs e)
        {
            XBOXPadDynamicButtonEvent(e.DPadInfo.GetType().GetMethods(), e.DPadInfo, keySet.PlayerList[0].DPadSet);
        }
        private void XBOXPad_StickEvent(XBOXPadStickEventArgs e)
        {
            object targetSet = keySet.PlayerList[0].StickSet;
            object settingObj = keySet.PlayerList[0].StickSet.Setting;

            foreach (System.Reflection.MethodInfo method in e.StickInfo.GetType().GetMethods())
            {
                if (method.Name.IndexOf("get_") != -1)
                {
                    System.Reflection.MethodInfo targetMethod = targetSet.GetType().GetMethod(method.Name);
                    string param = (string)targetMethod.Invoke(targetSet, null);

                    Microsoft.Xna.Framework.Vector2 vector2 =
                                (Microsoft.Xna.Framework.Vector2)method.Invoke(e.StickInfo, null);
                    int sensitivity = (int)settingObj.GetType()
                        .GetMethod(method.Name + "Sensitivity").Invoke(settingObj, null);

                    switch (param)
                    {
                        case "MouseMove":
                            MouseMoveEvent((int) System.Math.Truncate(vector2.X * sensitivity), 
                                -(int) System.Math.Truncate(vector2.Y * sensitivity));
                            break;
                        case "Detail":
                        default:
                            for (int i = 0; i < 4; i++)
                            {
                                float action = 0;
                                targetMethod = targetSet.GetType().GetMethod(method.Name + i);

                                if (i == 0 && vector2.X < 0) { action = vector2.X; }
                                if (i == 1 && vector2.X > 0) { action = vector2.X; }
                                if (i == 2 && vector2.Y > 0) { action = vector2.Y; }
                                if (i == 3 && vector2.Y < 0) { action = vector2.Y; }

                                param = (string)targetMethod.Invoke(targetSet, null);
                                XBOXPadSensitivityButtonEvent(method.Name + i + targetSet.GetType().Name, System.Math.Abs(action), (100 - sensitivity) * 0.01f, param);
                            }
                            break;
                    }
                }
            }
        }
        private void XBOXPad_TriggerEvent(XBOXPadTriggerEventArgs e)
        {
            object targetSet = keySet.PlayerList[0].TriggerSet;
            object settingObj = keySet.PlayerList[0].TriggerSet.Setting;

            foreach (System.Reflection.MethodInfo method in e.TriggersInfo.GetType().GetMethods())
            {
                if (method.Name.IndexOf("get_") != -1)
                {
                    float action = (float) method.Invoke(e.TriggersInfo, null);
                    int sensitivity = (int)settingObj.GetType()
                        .GetMethod(method.Name + "Sensitivity").Invoke(settingObj, null);

                    System.Reflection.MethodInfo targetMethod = targetSet.GetType().GetMethod(method.Name);
                    string param = (string)targetMethod.Invoke(targetSet, null);

                    XBOXPadSensitivityButtonEvent(method.Name + targetSet.GetType().Name, action, (100 - sensitivity) * 0.01f, param);
                }
            }
        }

        private void XBOXPadSensitivityButtonEvent(string key, float action, float sensitivity, string value)
        {
            if (!isKeyDown.ContainsKey(key)) { isKeyDown.Add(key, false); }

            if (action >= sensitivity)
            {
                isKeyDown[key] = true;
                KeyDownEvent(value);
            }

            if (action < sensitivity && (bool)isKeyDown[key])
            {
                isKeyDown[key] = false;
                KeyUpEvent(value);
            }
        }
        private void XBOXPadDynamicButtonEvent(System.Reflection.MethodInfo[] methods, object paramObj, object targetSet)
        {
            foreach (System.Reflection.MethodInfo method in methods)
            {
                if (method.Name.IndexOf("get_") != -1)
                {
                    if (!isKeyDown.ContainsKey(method.Name)) { isKeyDown.Add(method.Name, false); }

                    Microsoft.Xna.Framework.Input.ButtonState result =
                        (Microsoft.Xna.Framework.Input.ButtonState)method.Invoke(paramObj, null);
                    if (result == Microsoft.Xna.Framework.Input.ButtonState.Pressed)
                    {
                        System.Reflection.MethodInfo targetMethod = targetSet.GetType().GetMethod(method.Name);
                        string param = (string)targetMethod.Invoke(targetSet, null);

                        isKeyDown[method.Name] = true;
                        KeyDownEvent(param);
                    }

                    if (result == Microsoft.Xna.Framework.Input.ButtonState.Released
                        && (bool)isKeyDown[method.Name])
                    {
                        System.Reflection.MethodInfo targetMethod = targetSet.GetType().GetMethod(method.Name);
                        string param = (string)targetMethod.Invoke(targetSet, null);

                        isKeyDown[method.Name] = false;
                        KeyUpEvent(param);
                    }
                }
            }
        }

        public void KeyDownEvent(string value)
        {
            switch (value)
            {
                case "MouseLeftClick":
                    MouseKeyEvent(value, !(bool)isMouseDown[value], true, MOUSEEVENTF.LEFTDOWN);
                    break;
                case "MouseRightClick":
                    MouseKeyEvent(value, !(bool)isMouseDown[value], true, MOUSEEVENTF.RIGHTDOWN);
                    break;
                case "MouseMiddleClick":
                    MouseKeyEvent(value, !(bool)isMouseDown[value], true, MOUSEEVENTF.MIDDLEDOWN);
                    break;
                default:
                    User32dll.keybd_event(define.GetValue(value), 0, KEYEVENTF.SILENT, 0);
                    break;
            }
        }
        public void KeyUpEvent(string value)
        {
            switch (value)
            {
                case "MouseLeftClick":
                    MouseKeyEvent(value, (bool)isMouseDown[value], false, MOUSEEVENTF.LEFTUP);
                    break;
                case "MouseRightClick":
                    MouseKeyEvent(value, (bool)isMouseDown[value], false, MOUSEEVENTF.RIGHTUP);
                    break;
                case "MouseMiddleClick":
                    MouseKeyEvent(value, (bool)isMouseDown[value], false, MOUSEEVENTF.MIDDLEUP);
                    break;
                default:
                    User32dll.keybd_event(define.GetValue(value), 0, KEYEVENTF.SILENT | KEYEVENTF.KEYUP, 0);
                    break;
            }
        }
        public void MouseKeyEvent(string key, bool mouseDownFlag, bool changFlag, uint mouseeventf)
        {
            if (mouseDownFlag)
            {
                isMouseDown[key] = changFlag;
                User32dll.mouse_event(mouseeventf, 0, 0, 0, 0);
            }
        }
        public void MouseMoveEvent(int x, int y)
        {
            System.Drawing.Point point = new System.Drawing.Point();
            User32dll.GetCursorPos(ref point);
            User32dll.SetCursorPos(point.X + x, point.Y + y);
        }

        /**
         * 테스트 함수 구간
         */
        private void textBox1_KeyDown(object sender, System.Windows.Forms.KeyEventArgs e)
        {
            //textBox1.Text = e.KeyCode + "::0x" + string.Format("{00:X}", e.KeyValue);
            textBox1.Text = define.GetKey((byte) e.KeyValue);
        }

        private void MainForm_MouseClick(object sender, System.Windows.Forms.MouseEventArgs e)
        {
        }

        private void MainForm_MouseDown(object sender, System.Windows.Forms.MouseEventArgs e)
        {
            System.Console.WriteLine("::MouseDown");
        }

        /**
         * 메뉴 이벤트 구간
         */
        private void OpenToolStripMenuItem_Click(object sender, System.EventArgs e)
        {
            ConfigModel config = __BaseConfig.getConfig();

            string openPath = DialogUtil.getOpenFilePath(System.IO.Path.GetFullPath(config.ToString("OpenFileDialogPath")), "KetSet Files(*.xml)|*.xml");
            if (openPath != null)
            {
                config["OpenFileDialogPath"] = openPath.Substring(0, openPath.LastIndexOf('\\'));
                config["LastOpenKeysetPath"] = openPath;

                __BaseConfig.setConfig(config);
                keySet = __BaseSerializer.Read<KeySet>(openPath);
            }
            PrintBtnValue();
        }
        private void SaveToolStripMenuItem_Click(object sender, System.EventArgs e)
        {
            ConfigModel config = __BaseConfig.getConfig();

            __BaseSerializer.Write<KeySet>(keySet, config.ToString("LastOpenKeysetPath"));
        }
        private void SaveAsToolStripMenuItem_Click(object sender, System.EventArgs e)
        {
            ConfigModel config = __BaseConfig.getConfig();

            string savePath = DialogUtil.getOpenFilePath(System.IO.Path.GetFullPath(config.ToString("SaveFileDialogPath")), "KetSet Files(*.xml)|*.xml");
            if (savePath != null)
            {
                config["SaveFileDialogPath"] = savePath.Substring(0, savePath.LastIndexOf('\\'));
                config["LastOpenKeysetPath"] = savePath;

                __BaseConfig.setConfig(config);
                __BaseSerializer.Write<KeySet>(keySet, savePath);
            }
        }
        private void ExitToolStripMenuItem_Click(object sender, System.EventArgs e)
        {
            this.Close();
        }

        /**
         * 바인딩 이벤트 구간
         */
        private void BtnBinding_Click(object sender, System.EventArgs e)
        {
            ButtonBindingForm btnBindingFrm = new ButtonBindingForm(keySet, define,
                ((System.Windows.Forms.Button)sender).Name.Replace("Btn", ""));
            System.Windows.Forms.DialogResult result = btnBindingFrm.ShowDialog();
            if (result == System.Windows.Forms.DialogResult.OK)
            {
                PrintBtnValue();
            }
        }

        /**
         * 기타 함수 구간
         */
        private void PrintBtnValue()
        {
            foreach (System.Reflection.FieldInfo field in this.GetType().GetFields(
                         System.Reflection.BindingFlags.NonPublic |
                         System.Reflection.BindingFlags.Instance))
            {
                if (field.Name.Substring(0, 3).Equals("Btn"))
                {
                    foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].ButtonSet.GetType().GetMethods())
                    {
                        if (method.Name.Equals("get_" + field.Name.Replace("Btn", "")))
                        {
                            System.Windows.Forms.Button button = field.GetValue(this) as System.Windows.Forms.Button;
                            button.Text = method.Invoke(keySet.PlayerList[0].ButtonSet, null).ToString();
                        }
                    }
                    foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].DPadSet.GetType().GetMethods())
                    {
                        if (method.Name.Equals("get_" + field.Name.Replace("Btn", "")))
                        {
                            System.Windows.Forms.Button button = field.GetValue(this) as System.Windows.Forms.Button;
                            button.Text = method.Invoke(keySet.PlayerList[0].DPadSet, null).ToString();
                        }
                    }
                }
            }
        }

        private void BtnSticksLeft_Click(object sender, System.EventArgs e)
        {
            System.Windows.Forms.MessageBox.Show("UI를 완성 중에 있습니다. 편집은 XML을 이용하세요.", "알림");
        }
        private void BtnSticksRight_Click(object sender, System.EventArgs e)
        {
            System.Windows.Forms.MessageBox.Show("UI를 완성 중에 있습니다. 편집은 XML을 이용하세요.", "알림");
        }
    }
}
