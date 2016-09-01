using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Runtime.InteropServices;
using System.Diagnostics;

namespace Delete_Serverfile
{
    public class dllinjection
    {


        public const int PROCESS_ALL_ACCESS = 0xFFF;
        public const int PROCESS_WM_READ = 0x0010;
        public const int MAXIMUM_ALLOWED = 0x2000000;

        [DllImport("kernel32.dll")]
        public static extern IntPtr OpenProcess(int dwDesiredAccess, bool bInheritHandle, int dwProcessId);

        [DllImport("kernel32", CharSet = CharSet.Unicode, SetLastError = true)]
        public static extern IntPtr LoadLibrary(string lpFileName);

        [DllImport("kernel32", CharSet = CharSet.Ansi, SetLastError = true, ExactSpelling = false)]
        public static extern IntPtr GetProcAddress(IntPtr hModule, string lpProcName);

        [DllImport("kernel32.dll", SetLastError = true)]
        static extern IntPtr VirtualAllocEx(IntPtr hProcess, IntPtr lpAddress, IntPtr dwSize, uint flAllocationType, uint flProtect);

        [DllImport("kernel32.dll")]
        public static extern IntPtr GetModuleHandle(string lpModuleName);

        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern int WriteProcessMemory(IntPtr hProcess, IntPtr lpBaseAddress, byte[] buffer, uint size, int lpNumberOfBytesWritten);

        [DllImport("kernel32.dll", SetLastError = true)]
        public static extern IntPtr CreateRemoteThread(IntPtr hProcess, IntPtr lpThreadAttribute, IntPtr dwStackSize, IntPtr lpStartAddress, IntPtr lpParameter, uint dwCreationFlags, IntPtr lpThreadId);

        [DllImport("kernel32.dll", SetLastError = true)]
        static extern int CloseHandle(IntPtr hObject);

        [DllImport("kernel32.dll", SetLastError = true)]
        static extern UInt32 WaitForSingleObject(IntPtr hHandle, UInt32 dwMilliseconds);

        //API 사용을 위한 선언

        public bool injectiondll(string proname, string dllpaths)
        {
            Process[] ProcList; //프로세스 리스트를 배열로 받아온다

            ProcList = Process.GetProcessesByName(proname); //배열에서 proname을 찾는다

            int ProPid = ProcList[0].Id; //propid에 프로세스 pid를 담는다

                IntPtr processhwn = OpenProcess(PROCESS_ALL_ACCESS, false, ProPid); // 프로세스의 핸들을 구한다

                if (processhwn == (IntPtr)0)
                {
                    return false;
                }

                IntPtr LoadlibAdr = GetProcAddress(GetModuleHandle("kernel32.dll"), "LoadLibraryA");

                if (LoadlibAdr == (IntPtr)0)
                {
                    return false;
                }

                byte[] bytes = Encoding.GetEncoding(949).GetBytes(dllpaths);
                /*
                System.Text.ASCIIEncoding ASCII  = new System.Text.ASCIIEncoding();
                Byte[] bytes = ASCII.GetBytes(dllpaths);
                */

                //     byte[] bytes = Encoding.UTF8.GetBytes(dllpaths); 


                IntPtr lngAlloc = VirtualAllocEx(processhwn, (IntPtr)null, (IntPtr)(bytes.Length + 1), 0x1000, 0X40);

                if (lngAlloc == (IntPtr)0)
                {
                    return false;
                }

                if (WriteProcessMemory(processhwn, lngAlloc, bytes, (uint)bytes.Length + 1, 0) == 0)
                {
                    return false;
                }

                IntPtr CRT = CreateRemoteThread(processhwn, (IntPtr)0, (IntPtr)null, LoadlibAdr, lngAlloc, 0, (IntPtr)0);
                if (CRT == (IntPtr)0)
                {
                    return false;
                }
                else
                {
                    WaitForSingleObject(CRT, 0xFFFFFFFF);
                    return true;
                }
                CloseHandle(processhwn);
                CloseHandle(CRT);
            }
    }
}
