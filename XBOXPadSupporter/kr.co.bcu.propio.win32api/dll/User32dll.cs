namespace kr.co.bcu.propio.win32api.dll
{
    public class User32dll
    {
        #region SetWindowsHook() codes

        public const int WH_MIN              = (-1);
        public const int WH_MSGFILTER        = (-1);
        public const int WH_JOURNALRECORD    = 0;
        public const int WH_JOURNALPLAYBACK  = 1;
        /// <summary>
        /// Keyboard Message
        /// </summary>
        public const int WH_KEYBOARD         = 2;
        /// <summary>
        /// Meesage Queue의 Message 감시
        /// </summary>
        public const int WH_GETMESSAGE       = 3;
        /// <summary>
        /// Window Procedure Hooking
        /// </summary>
        public const int WH_CALLWNDPROC      = 4;
        public const int WH_CBT              = 5;
        public const int WH_SYSMSGFILTER     = 6;
        /// <summary>
        /// Mouse Message
        /// </summary>
        public const int WH_MOUSE            = 7;
        public const int WH_HARDWARE         = 8;
        public const int WH_DEBUG            = 9;
        public const int WH_SHELL            = 10;
        public const int WH_FOREGROUNDIDLE   = 11;
        public const int WH_CALLWNDPROCRET   = 12;
        /// <summary>
        /// Low Level Keyboard Hook
        /// </summary>
        public const int WH_KEYBOARD_LL      = 13;
        /// <summary>
        /// Low Level Mouse Hook
        /// </summary>
        public const int WH_MOUSE_LL         = 14;
        #endregion

        #region User32dll delegate

        public delegate System.IntPtr HookProc(int nCode, int wParam, System.IntPtr lParam);
        public delegate System.IntPtr LowLevelKeyboardProc(int nCode, System.IntPtr wParam, ref KBDLLHOOKSTRUCT lParam);
        public delegate System.IntPtr LowLevelMouseProc(int nCode, System.IntPtr wParam, ref MSLLHOOKSTRUCT lParam);

        #endregion

        #region User32dll API

        /// <summary>
        /// 
        /// </summary>
        /// <param name="idHook">후킹할 메시지 타입</param>
        /// <param name="lpfn">후킹 메세지가 전달될 프로시저 주소(Func Pointer) [format:private System.IntPtr FuncName(int nCode, System.IntPtr wParam, System.IntPtr lParam)]</param>
        /// <param name="hMod">lpfn에 전달된 프로시저를 포함하는 모듈의 인스턴스 핸들</param>
        /// <param name="dwThreadId">쓰레드 식별자, 0이면 호출된 쓰레드를 나타낸다.</param>
        /// <returns>성공:설치된 훅의 핸들값/실패:0</returns>
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern System.IntPtr SetWindowsHookEx(int idHook, HookProc lpfn, System.IntPtr hMod, uint dwThreadId);
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern System.IntPtr SetWindowsHookEx(int idHook, LowLevelKeyboardProc lpfn, System.IntPtr hMod, uint dwThreadId);
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern System.IntPtr SetWindowsHookEx(int idHook, LowLevelMouseProc lpfn, System.IntPtr hMod, uint dwThreadId);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="hhk"></param>
        /// <returns></returns>
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern bool UnhookWindowsHookEx(System.IntPtr hhk);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="hhk"></param>
        /// <param name="nCode"></param>
        /// <param name="wParam"></param>
        /// <param name="lParam"></param>
        /// <returns></returns>
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern System.IntPtr CallNextHookEx(System.IntPtr hhk, int nCode, System.IntPtr wParam, System.IntPtr lParam);
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern System.IntPtr CallNextHookEx(System.IntPtr hhk, int nCode, System.IntPtr wParam, ref KBDLLHOOKSTRUCT lParam);
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern System.IntPtr CallNextHookEx(System.IntPtr hhk, int nCode, System.IntPtr wParam, ref MSLLHOOKSTRUCT lParam);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="bVk"></param>
        /// <param name="bScan"></param>
        /// <param name="dwFlags"></param>
        /// <param name="dwExtraInfo"></param>
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern void keybd_event(byte bVk, byte bScan, uint dwFlags, uint dwExtraInfo);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="dwFlags"></param>
        /// <param name="dx"></param>
        /// <param name="dy"></param>
        /// <param name="dwData"></param>
        /// <param name="dwExtraInfo"></param>
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern void mouse_event(uint dwFlags, uint dx, uint dy, uint dwData, int dwExtraInfo);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="lpPoint"></param>
        /// <returns></returns>
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern bool GetCursorPos(ref System.Drawing.Point lpPoint);

        /// <summary>
        /// 
        /// </summary>
        /// <param name="X"></param>
        /// <param name="Y"></param>
        /// <returns></returns>
        [System.Runtime.InteropServices.DllImport("user32.dll")]
        public static extern bool SetCursorPos(int X, int Y);

        #endregion

        #region User32dll Struct

        /// <summary>
        /// 
        /// </summary>
        public struct KBDLLHOOKSTRUCT
        {
            public int vkCode;
            public int scanCode;
            public int flags;
            public int time;
            public int dwExtraInfo;
        }

        /// <summary>
        /// 
        /// </summary>
        public struct MSLLHOOKSTRUCT
        {
            public System.Drawing.Point pt;
            public int mouseData;
            public int flags;
            public int time;
            public long dwExtraInfo;
        }

        #endregion
    }
}
