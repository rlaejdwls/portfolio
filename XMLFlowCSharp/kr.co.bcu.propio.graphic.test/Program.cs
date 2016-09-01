using kr.co.bcu.propio.graphic.core;

namespace kr.co.bcu.propio.graphic.test
{
    static class Program
    {
        /// <summary>
        /// 해당 응용 프로그램의 주 진입점입니다.
        /// </summary>
        [System.STAThread]
        static void Main()
        {
            System.Windows.Forms.Application.EnableVisualStyles();
            System.Windows.Forms.Application.SetCompatibleTextRenderingDefault(false);
            System.Windows.Forms.Application.Run(new MainForm());
            //System.Windows.Forms.Application.Run(new TestForm());
        }
    }
}
