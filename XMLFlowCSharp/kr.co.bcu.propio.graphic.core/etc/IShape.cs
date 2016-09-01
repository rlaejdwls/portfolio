using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;

namespace GraphicExampleCSharp.data
{
    interface IShape
    {
        bool getPtInRect(int x, int y);

        void setDownX(int downX);
        void setDownY(int downY);

        void onDraw(Graphics g);
        void move(int x, int y);
    }
}
