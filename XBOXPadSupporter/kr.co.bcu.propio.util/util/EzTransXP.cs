namespace kr.co.bcu.propio.util.util
{
    public class EzTransXP
    {
        [System.Runtime.InteropServices.DllImport(@".\dll\ezTransXP.dll")]
        private static extern bool OnPluginInit(System.IntPtr hSettingWnd, System.Text.StringBuilder cszSettingStringBuffer);
        [System.Runtime.InteropServices.DllImport(@".\dll\ezTransXP.dll")]
        private static extern bool OnPluginOption();
        [System.Runtime.InteropServices.DllImport(@".\dll\ezTransXP.dll")]
        private static extern bool OnPluginClose();
        [System.Runtime.InteropServices.DllImport(@".\dll\ezTransXP.dll")]
        private static extern bool Translate(byte[] cszJapanese, System.Text.StringBuilder szKorean, int nBufSize);

        public static bool initialize(System.IntPtr hwnd, System.Text.StringBuilder cszSettingStringBuffer)
        {
            if (!System.IO.File.Exists(@".\dll\ezTransXP.dll"))
            {
                System.Reflection.Assembly assembly = System.Reflection.Assembly.GetExecutingAssembly();
                if (!System.IO.Directory.Exists(@".\dll")) System.IO.Directory.CreateDirectory(@".\dll");
                System.IO.FileStream sw = null;
                
                try
                {
                    System.IO.Stream sr = assembly.GetManifestResourceStream("kr.co.bcu.propio.util.dll.ezTransXP.dll");
                    sw = new System.IO.FileStream(@".\dll\ezTransXP.dll", System.IO.FileMode.Create, System.IO.FileAccess.Write);

                    byte[] buffer = new byte[1024];
                    int count = 0;
                    while ((count = sr.Read(buffer, 0, buffer.Length)) != 0)
                    {
                        sw.Write(buffer, 0, buffer.Length);
                    }
                }
                catch (System.Exception exception)
                {
                    System.Console.WriteLine(exception.Message);
                }
                finally
                {
                    if (sw != null) { sw.Close(); }
                }
            }

            return OnPluginInit(hwnd, cszSettingStringBuffer);
        }

        public static string Translate(string japanese, int buffSize)
        {
            System.Text.Encoding encoding = System.Text.Encoding.GetEncoding("Shift_JIS");
            byte[] __bytes = encoding.GetBytes(japanese);

            System.Text.StringBuilder korean = new System.Text.StringBuilder(buffSize);
            bool next = false;
            try
            {
                next = EzTransXP.Translate(__bytes, korean, buffSize);
            }
            catch (System.Exception e)
            {
                System.Windows.Forms.MessageBox.Show(e.Message);
            }

            if (next) return korean.ToString();
            else return japanese;
        }
    }
}
