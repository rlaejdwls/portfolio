using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace kr.co.bcu.propio.win32api.dll
{
    public class Kernel32dll
    {
        #region Kernel32dll API

        /// <summary>
        /// 
        /// </summary>
        /// <param name="lpModuleName"></param>
        /// <returns></returns>
        [System.Runtime.InteropServices.DllImport("kernel32.dll")]
        public static extern System.IntPtr GetModuleHandle(string lpModuleName);

        #endregion
    }
}
