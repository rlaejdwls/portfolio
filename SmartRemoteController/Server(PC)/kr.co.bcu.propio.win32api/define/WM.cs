﻿//#define _WIN32_WCE        //Windows CE Application
#define _WIN32_VISTA      //Windows Vista

namespace kr.co.bcu.propio.win32api.define
{
    public class WM 
    {
        public const int NULL                         = 0x0000;
        public const int CREATE                       = 0x0001;
        public const int DESTROY                      = 0x0002;
        public const int MOVE                         = 0x0003;
        public const int SIZE                         = 0x0005;

        public const int ACTIVATE                     = 0x0006;

        public const int SETFOCUS                     = 0x0007;
        public const int KILLFOCUS                    = 0x0008;
        public const int ENABLE                       = 0x000A;
        public const int SETREDRAW                    = 0x000B;
        public const int SETTEXT                      = 0x000C;
        public const int GETTEXT                      = 0x000D;
        public const int GETTEXTLENGTH                = 0x000E;
        public const int PAINT                        = 0x000F;
        public const int CLOSE                        = 0x0010;
#if _WIN32_WCE
        public const int QUERYENDSESSION              = 0x0011;
        public const int QUERYOPEN                    = 0x0013;
        public const int ENDSESSION                   = 0x0016;
#endif
        public const int QUIT                         = 0x0012;
        public const int ERASEBKGND                   = 0x0014;
        public const int SYSCOLORCHANGE               = 0x0015;
        public const int SHOWWINDOW                   = 0x0018;
        public const int WININICHANGE                 = 0x001A;
        public const int SETTINGCHANGE                = WININICHANGE;
          
        public const int DEVMODECHANGE                = 0x001B;
        public const int ACTIVATEAPP                  = 0x001C;
        public const int FONTCHANGE                   = 0x001D;
        public const int TIMECHANGE                   = 0x001E;
        public const int CANCELMODE                   = 0x001F;
        public const int SETCURSOR                    = 0x0020;
        public const int MOUSEACTIVATE                = 0x0021;
        public const int CHILDACTIVATE                = 0x0022;
        public const int QUEUESYNC                    = 0x0023;
                        
        public const int GETMINMAXINFO                = 0x0024;
            
        public const int PAINTICON                    = 0x0026;
        public const int ICONERASEBKGND               = 0x0027;
        public const int NEXTDLGCTL                   = 0x0028;
        public const int SPOOLERSTATUS                = 0x002A;
        public const int DRAWITEM                     = 0x002B;
        public const int MEASUREITEM                  = 0x002C;
        public const int DELETEITEM                   = 0x002D;
        public const int VKEYTOITEM                   = 0x002E;
        public const int CHARTOITEM                   = 0x002F;
        public const int SETFONT                      = 0x0030;
        public const int GETFONT                      = 0x0031;
        public const int SETHOTKEY                    = 0x0032;
        public const int GETHOTKEY                    = 0x0033;
        public const int QUERYDRAGICON                = 0x0037;
        public const int COMPAREITEM                  = 0x0039;
#if _WIN32_WCE          
        public const int GETOBJECT                    = 0x003D;
#endif                   
        public const int COMPACTING                   = 0x0041;
        public const int COMMNOTIFY                   = 0x0044;  /* no longer suported */
        public const int WINDOWPOSCHANGING            = 0x0046;
        public const int WINDOWPOSCHANGED             = 0x0047;
                         
        public const int POWER                        = 0x0048;
                     
        public const int COPYDATA                     = 0x004A;
        public const int CANCELJOURNAL                = 0x004B;
                  
        public const int NOTIFY                       = 0x004E;
        public const int INPUTLANGCHANGEREQUEST       = 0x0050;
        public const int INPUTLANGCHANGE              = 0x0051;
        public const int TCARD                        = 0x0052;
        public const int HELP                         = 0x0053;
        public const int USERCHANGED                  = 0x0054;
        public const int NOTIFYFORMAT                 = 0x0055;
                           
        public const int CONTEXTMENU                  = 0x007B;
        public const int STYLECHANGING                = 0x007C;
        public const int STYLECHANGED                 = 0x007D;
        public const int DISPLAYCHANGE                = 0x007E;
        public const int GETICON                      = 0x007F;
        public const int SETICON                      = 0x0080;
                   
        public const int NCCREATE                     = 0x0081;
        public const int NCDESTROY                    = 0x0082;
        public const int NCCALCSIZE                   = 0x0083;
        public const int NCHITTEST                    = 0x0084;
        public const int NCPAINT                      = 0x0085;
        public const int NCACTIVATE                   = 0x0086;
        public const int GETDLGCODE                   = 0x0087;
#if _WIN32_WCE                 
        public const int SYNCPAINT                    = 0x0088;
#endif                   
        public const int NCMOUSEMOVE                  = 0x00A0;
        public const int NCLBUTTONDOWN                = 0x00A1;
        public const int NCLBUTTONUP                  = 0x00A2;
        public const int NCLBUTTONDBLCLK              = 0x00A3;
        public const int NCRBUTTONDOWN                = 0x00A4;
        public const int NCRBUTTONUP                  = 0x00A5;
        public const int NCRBUTTONDBLCLK              = 0x00A6;
        public const int NCMBUTTONDOWN                = 0x00A7;
        public const int NCMBUTTONUP                  = 0x00A8;
        public const int NCMBUTTONDBLCLK              = 0x00A9;
                            
        public const int NCXBUTTONDOWN                = 0x00AB;
        public const int NCXBUTTONUP                  = 0x00AC;
        public const int NCXBUTTONDBLCLK              = 0x00AD;
                             
        public const int INPUT_DEVICE_CHANGE          = 0x00FE;

        public const int INPUT                        = 0x00FF;
          
        public const int KEYFIRST                     = 0x0100;
        public const int KEYDOWN                      = 0x0100;
        public const int KEYUP                        = 0x0101;
        public const int CHAR                         = 0x0102;
        public const int DEADCHAR                     = 0x0103;
        public const int SYSKEYDOWN                   = 0x0104;
        public const int SYSKEYUP                     = 0x0105;
        public const int SYSCHAR                      = 0x0106;
        public const int SYSDEADCHAR                  = 0x0107;
         
        public const int UNICHAR                      = 0x0109;
        public const int KEYLAST                      = 0x0109;
                 
        public const int IME_STARTCOMPOSITION         = 0x010D;
        public const int IME_ENDCOMPOSITION           = 0x010E;
        public const int IME_COMPOSITION              = 0x010F;
        public const int IME_KEYLAST                  = 0x010F;
                        
        public const int INITDIALOG                   = 0x0110;
        public const int COMMAND                      = 0x0111;
        public const int SYSCOMMAND                   = 0x0112;
        public const int TIMER                        = 0x0113;
        public const int HSCROLL                      = 0x0114;
        public const int VSCROLL                      = 0x0115;
        public const int INITMENU                     = 0x0116;
        public const int INITMENUPOPUP                = 0x0117;
        public const int MENUSELECT                   = 0x011F;
        public const int MENUCHAR                     = 0x0120;
        public const int ENTERIDLE                    = 0x0121;

#if _WIN32_WCE           
        public const int MENURBUTTONUP                = 0x0122;
        public const int MENUDRAG                     = 0x0123;
        public const int MENUGETOBJECT                = 0x0124;
        public const int UNINITMENUPOPUP              = 0x0125;
        public const int MENUCOMMAND                  = 0x0126;

        public const int CHANGEUISTATE                = 0x0127;
        public const int UPDATEUISTATE                = 0x0128;
        public const int QUERYUISTATE                 = 0x0129;
#endif                           

        public const int CTLCOLORMSGBOX               = 0x0132;
        public const int CTLCOLOREDIT                 = 0x0133;
        public const int CTLCOLORLISTBOX              = 0x0134;
        public const int CTLCOLORBTN                  = 0x0135;
        public const int CTLCOLORDLG                  = 0x0136;
        public const int CTLCOLORSCROLLBAR            = 0x0137;
        public const int CTLCOLORSTATIC               = 0x0138;
        public const int GETHMENU                     = 0x01E1;

        public const int MOUSEFIRST                   = 0x0200;
        public const int MOUSEMOVE                    = 0x0200;
        public const int LBUTTONDOWN                  = 0x0201;
        public const int LBUTTONUP                    = 0x0202;
        public const int LBUTTONDBLCLK                = 0x0203;
        public const int RBUTTONDOWN                  = 0x0204;
        public const int RBUTTONUP                    = 0x0205;
        public const int RBUTTONDBLCLK                = 0x0206;
        public const int MBUTTONDOWN                  = 0x0207;
        public const int MBUTTONUP                    = 0x0208;
        public const int MBUTTONDBLCLK                = 0x0209;

        public const int MOUSEWHEEL                   = 0x020A;

        public const int XBUTTONDOWN                  = 0x020B;
        public const int XBUTTONUP                    = 0x020C;
        public const int XBUTTONDBLCLK                = 0x020D;

        public const int MOUSEHWHEEL                  = 0x020E;

#if _WIN32_VISTA      
        public const int MOUSELAST                    = 0x020E;
#else                    
        public const int MOUSELAST                    = 0x020D;
#endif                     

        public const int PARENTNOTIFY                 = 0x0210;
        public const int ENTERMENULOOP                = 0x0211;
        public const int EXITMENULOOP                 = 0x0212;

        public const int NEXTMENU                     = 0x0213;
        public const int SIZING                       = 0x0214;
        public const int CAPTURECHANGED               = 0x0215;
        public const int MOVING                       = 0x0216;

        public const int POWERBROADCAST               = 0x0218;

        public const int DEVICECHANGE                 = 0x0219;

        public const int MDICREATE                    = 0x0220;
        public const int MDIDESTROY                   = 0x0221;
        public const int MDIACTIVATE                  = 0x0222;
        public const int MDIRESTORE                   = 0x0223;
        public const int MDINEXT                      = 0x0224;
        public const int MDIMAXIMIZE                  = 0x0225;
        public const int MDITILE                      = 0x0226;
        public const int MDICASCADE                   = 0x0227;
        public const int MDIICONARRANGE               = 0x0228;
        public const int MDIGETACTIVE                 = 0x0229;

        public const int MDISETMENU                   = 0x0230;
        public const int ENTERSIZEMOVE                = 0x0231;
        public const int EXITSIZEMOVE                 = 0x0232;
        public const int DROPFILES                    = 0x0233;
        public const int MDIREFRESHMENU               = 0x0234;

        public const int IME_SETCONTEXT               = 0x0281;
        public const int IME_NOTIFY                   = 0x0282;
        public const int IME_CONTROL                  = 0x0283;
        public const int IME_COMPOSITIONFULL          = 0x0284;
        public const int IME_SELECT                   = 0x0285;
        public const int IME_CHAR                     = 0x0286;

        public const int IME_REQUEST                  = 0x0288;

        public const int IME_KEYDOWN                  = 0x0290;
        public const int IME_KEYUP                    = 0x0291;

        public const int NCMOUSEHOVER                 = 0x02A0;
        public const int NCMOUSELEAVE                 = 0x02A2;
                           
        public const int WTSSESSION_CHANGE            = 0x02B1;
                                 
        public const int TABLET_FIRST                 = 0x02c0;
        public const int TABLET_LAST                  = 0x02df;
                            
        public const int CUT                          = 0x0300;
        public const int COPY                         = 0x0301;
        public const int PASTE                        = 0x0302;
        public const int CLEAR                        = 0x0303;
        public const int UNDO                         = 0x0304;
        public const int RENDERFORMAT                 = 0x0305;
        public const int RENDERALLFORMATS             = 0x0306;
        public const int DESTROYCLIPBOARD             = 0x0307;
        public const int DRAWCLIPBOARD                = 0x0308;
        public const int PAINTCLIPBOARD               = 0x0309;
        public const int VSCROLLCLIPBOARD             = 0x030A;
        public const int SIZECLIPBOARD                = 0x030B;
        public const int ASKCBFORMATNAME              = 0x030C;
        public const int CHANGECBCHAIN                = 0x030D;
        public const int HSCROLLCLIPBOARD             = 0x030E;
        public const int QUERYNEWPALETTE              = 0x030F;
        public const int PALETTEISCHANGING            = 0x0310;
        public const int PALETTECHANGED               = 0x0311;
        public const int HOTKEY                       = 0x0312;
                        
        public const int PRINT                        = 0x0317;
        public const int PRINTCLIENT                  = 0x0318;
                                   
        public const int APPCOMMAND                   = 0x0319;
                                       
        public const int THEMECHANGED                 = 0x031A;
                                        
        public const int CLIPBOARDUPDATE              = 0x031D;

#if _WIN32_VISTA                            
        public const int DWMCOMPOSITIONCHANGED        = 0x031E;
        public const int DWMNCRENDERINGCHANGED        = 0x031F;
        public const int DWMCOLORIZATIONCOLORCHANGED  = 0x0320;
        public const int DWMWINDOWMAXIMIZEDCHANGE     = 0x0321;
#endif                                       

#if _WIN32_VISTA                             
        public const int GETTITLEBARINFOEX            = 0x033F;
#endif

        public const int HANDHELDFIRST                = 0x0358;
        public const int HANDHELDLAST                 = 0x035F;

        public const int AFXFIRST                     = 0x0360;
        public const int AFXLAST                      = 0x037F;

        public const int PENWINFIRST                  = 0x0380;
        public const int PENWINLAST                   = 0x038F;

        public const int APP                          = 0x8000;

        /*
         * NOTE: All Message Numbers below 0x0400 are RESERVED.
         *
         * Private Window Messages Start Here:
         */
        public const int USER                         = 0x0400;
    }
}
