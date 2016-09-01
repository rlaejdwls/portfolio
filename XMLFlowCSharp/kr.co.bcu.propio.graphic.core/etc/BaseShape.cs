using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Drawing;

namespace GraphicExampleCSharp.data
{
    public class BaseShape : IShape
    {
        private Pen pen;
        private Rectangle rect;

        private int downX;
        private int downY;

        public BaseShape()
        {
            pen = new Pen(Color.Green, 2);
            rect = new Rectangle(100, 100, 100, 100);
        }

        public void setDownX(int downX)
        {
            this.downX = downX - rect.Left;
        }

        public void setDownY(int downY)
        {
            this.downY = downY - rect.Top;
        }

        public bool getPtInRect(int x, int y)
        {
            if (rect.Left < x &&
                rect.Right > x &&
                rect.Top < y &&
                rect.Bottom > y)
            {
                return true;
            }
            return false;
        }

        public void onDraw(Graphics g)
        {
            g.DrawRectangle(pen, rect);
        }

        public void move(int x, int y)
        {
            rect.X = x - downX;
            rect.Y = y - downY;
        }
    }
}
