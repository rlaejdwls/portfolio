using com.tistory.feelmcsna.controllersupporter.keyset.model;
using com.tistory.feelmcsna.controllersupporter.define.model;

namespace com.tistory.feelmcsna.controllersupporter
{
    public partial class ButtonBindingForm : System.Windows.Forms.Form
    {
        private KeySet keySet;
        private Define define;

        private string btnName;
        private string backupKey;

        private bool isOK = false;
        private bool isEnter = false;

        public ButtonBindingForm(KeySet keySet, Define define, string btnName)
        {
            InitializeComponent();

            this.keySet = keySet;
            this.define = define;
            this.btnName = btnName;

            this.KeyPreview = true;
        }

        private void ButtonBindingForm_Load(object sender, System.EventArgs e)
        {
            backupKey = PrintBtnValue();
        }
        private void ButtonBindingForm_KeyDown(object sender, System.Windows.Forms.KeyEventArgs e)
        {
            SetBtnValue(define.GetKey((byte)e.KeyValue));
            e.Handled = true;
        }
        private void ButtonBindingForm_KeyUp(object sender, System.Windows.Forms.KeyEventArgs e)
        {
            this.Focus();
        }
        private void BtnOK_PreviewKeyDown(object sender, System.Windows.Forms.PreviewKeyDownEventArgs e)
        {
            if (e.KeyCode == System.Windows.Forms.Keys.Enter)
            {
                isEnter = true;
                SetBtnValue(define.GetKey((byte)e.KeyValue));
            }
        }
        private void BtnOK_Click(object sender, System.EventArgs e)
        {
            if (!isEnter)
            {
                isOK = true;
                this.DialogResult = System.Windows.Forms.DialogResult.OK;
                this.Close();
            }
            else
            {
                isEnter = false;
            }
        }
        private void ButtonBindingForm_FormClosing(object sender, System.Windows.Forms.FormClosingEventArgs e)
        {
            if (!isOK)
            {
                SetBtnValue(backupKey);
                this.DialogResult = System.Windows.Forms.DialogResult.Cancel;
            }
        }
        private void SetBtnValue(string key)
        {
            try
            {
                foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].ButtonSet.GetType().GetMethods())
                {
                    if (method.Name.Equals("set_" + btnName))
                    {
                        method.Invoke(keySet.PlayerList[0].ButtonSet, new object[] { key });
                    }
                }
                foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].DPadSet.GetType().GetMethods())
                {
                    if (method.Name.Equals("set_" + btnName))
                    {
                        method.Invoke(keySet.PlayerList[0].DPadSet, new object[] { key });
                    }
                }
                foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].StickSet.GetType().GetMethods())
                {
                    if (method.Name.Equals("set_" + btnName))
                    {
                        method.Invoke(keySet.PlayerList[0].StickSet, new object[] { key });
                    }
                }
                foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].TriggerSet.GetType().GetMethods())
                {
                    if (method.Name.Equals("set_" + btnName))
                    {
                        method.Invoke(keySet.PlayerList[0].TriggerSet, new object[] { key });
                    }
                }
                PrintBtnValue();
            }
            catch (System.Exception exc)
            {
                System.Windows.Forms.MessageBox.Show("ButtonBindingForm::" + exc.Message, "알림");
            }
        }
        private string PrintBtnValue()
        {
            try
            {
                object btnValue = "";
                foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].ButtonSet.GetType().GetMethods())
                {
                    if (method.Name.Equals("get_" + btnName))
                    {
                        btnValue = method.Invoke(keySet.PlayerList[0].ButtonSet, null);
                    }
                }
                foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].DPadSet.GetType().GetMethods())
                {
                    if (method.Name.Equals("get_" + btnName))
                    {
                        btnValue = method.Invoke(keySet.PlayerList[0].DPadSet, null);
                    }
                }
                foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].StickSet.GetType().GetMethods())
                {
                    if (method.Name.Equals("get_" + btnName))
                    {
                        btnValue = method.Invoke(keySet.PlayerList[0].StickSet, null);
                    }
                }
                foreach (System.Reflection.MethodInfo method in keySet.PlayerList[0].TriggerSet.GetType().GetMethods())
                {
                    if (method.Name.Equals("get_" + btnName))
                    {
                        btnValue = method.Invoke(keySet.PlayerList[0].TriggerSet, null);
                    }
                }
                LblBtnValue.Text = btnValue.ToString();
                return btnValue.ToString();
            }
            catch (System.Exception exc)
            {
                System.Windows.Forms.MessageBox.Show("ButtonBindingForm::" + exc.Message, "알림");
                return null;
            }
        }
    }
}
