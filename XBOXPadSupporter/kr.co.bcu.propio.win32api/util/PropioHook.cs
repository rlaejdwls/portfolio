using kr.co.bcu.propio.win32api.define;
using kr.co.bcu.propio.win32api.dll;

namespace kr.co.bcu.propio.win32api.util
{
    public class PropioHook
    {
        public const int KEY_IGNORED = 0x33;

        public System.IntPtr HwndKeyboard { get; set; }
        public System.IntPtr HwndMouse { get; set; }

        public event User32dll.LowLevelKeyboardProc OnLowLevelKeyboardHook;
        public event User32dll.LowLevelMouseProc OnLowLevelMouseHook;

        public bool bInstallKeyboardHook = false;
        public bool bInstallMouseHook = false;
        
        public PropioHook(bool bInstallKeyboardHook, bool bInstallMouseHook)
        {
            HwndKeyboard = System.IntPtr.Zero;
            this.bInstallKeyboardHook = bInstallKeyboardHook;
            this.bInstallMouseHook = bInstallMouseHook;
        }

        public void Start()
        {
            using (System.Diagnostics.Process curProcess = System.Diagnostics.Process.GetCurrentProcess())
            using (System.Diagnostics.ProcessModule curModule = curProcess.MainModule)
            {
                if (bInstallKeyboardHook)
                {
                    HwndKeyboard = User32dll.SetWindowsHookEx(
                        User32dll.WH_KEYBOARD_LL,
                        OnLowLevelKeyboardHook,
                        Kernel32dll.GetModuleHandle(curModule.ModuleName),
                        0
                    );
                }
                if (bInstallMouseHook)
                {
                    HwndMouse = User32dll.SetWindowsHookEx(
                        User32dll.WH_MOUSE_LL,
                        OnLowLevelMouseHook,
                        Kernel32dll.GetModuleHandle(curModule.ModuleName),
                        0
                    );
                }
            }
        }

        public void Stop()
        {
            if (bInstallKeyboardHook) { User32dll.UnhookWindowsHookEx(HwndKeyboard); }
            if (bInstallMouseHook) { User32dll.UnhookWindowsHookEx(HwndMouse); }
        }
    }
}
