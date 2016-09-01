using kr.co.bcu.propio.graphic.core;

namespace kr.co.bcu.propio.graphic.test
{
    public partial class MainForm : System.Windows.Forms.Form
    {
        kr.co.bcu.propio.graphic.core.baseflow.FlowManager manager =
                new kr.co.bcu.propio.graphic.core.baseflow.FlowManager();

        public MainForm()
        {
            InitializeComponent();
            __BaseProcess process = manager.initialize();
            txtView.Text = process.GetType().Name;
        }

        private void btnRight_Click(object sender, System.EventArgs e)
        {
            __BaseProcess process = manager.nextFlowline("RIGHT");
            txtView.Text = process.GetType().Name;
        }

        private void btnLeft_Click(object sender, System.EventArgs e)
        {
            __BaseProcess process = manager.nextFlowline("LEFT");
            txtView.Text = process.GetType().Name;
        }

        private void btnIf_Click(object sender, System.EventArgs e)
        {
            System.DateTime start = System.DateTime.Now;
            System.Console.WriteLine("StartIf");
            //for (int i = 0; i < 100000; i++)
            //{
                //System.Console.Write(i + ":");
                System.Random ran = new System.Random();
                int j = 29;//ran.Next(0, 9);

                if      (j ==  0) { System.Console.Write(j + ":"); }
                else if (j ==  1) { System.Console.Write(j + ":"); }
                else if (j ==  2) { System.Console.Write(j + ":"); }
                else if (j ==  3) { System.Console.Write(j + ":"); }
                else if (j ==  4) { System.Console.Write(j + ":"); }
                else if (j ==  5) { System.Console.Write(j + ":"); }
                else if (j ==  6) { System.Console.Write(j + ":"); }
                else if (j ==  7) { System.Console.Write(j + ":"); }
                else if (j ==  8) { System.Console.Write(j + ":"); }
                else if (j ==  9) { System.Console.Write(j + ":"); }
                else if (j == 10) { System.Console.Write(j + ":"); }
                else if (j == 11) { System.Console.Write(j + ":"); }
                else if (j == 12) { System.Console.Write(j + ":"); }
                else if (j == 13) { System.Console.Write(j + ":"); }
                else if (j == 14) { System.Console.Write(j + ":"); }
                else if (j == 15) { System.Console.Write(j + ":"); }
                else if (j == 16) { System.Console.Write(j + ":"); }
                else if (j == 17) { System.Console.Write(j + ":"); }
                else if (j == 18) { System.Console.Write(j + ":"); }
                else if (j == 19) { System.Console.Write(j + ":"); }
                else if (j == 20) { System.Console.Write(j + ":"); }
                else if (j == 21) { System.Console.Write(j + ":"); }
                else if (j == 22) { System.Console.Write(j + ":"); }
                else if (j == 23) { System.Console.Write(j + ":"); }
                else if (j == 24) { System.Console.Write(j + ":"); }
                else if (j == 25) { System.Console.Write(j + ":"); }
                else if (j == 26) { System.Console.Write(j + ":"); }
                else if (j == 27) { System.Console.Write(j + ":"); }
                else if (j == 28) { System.Console.Write(j + ":"); }
                else if (j == 29) { System.Console.Write(j + ":"); }
            //}
            System.Console.WriteLine();
            System.Console.WriteLine("EndIf");
            System.Console.WriteLine(System.DateTime.Now - start);
        }

        private void btnSwitch_Click(object sender, System.EventArgs e)
        {
            System.DateTime start = System.DateTime.Now;
            System.Console.WriteLine("StartIf");
            for (int i = 0; i < 100000; i++)
            {
                //System.Console.Write(i + ":");
                System.Random ran = new System.Random();
                int j = 29;//ran.Next(0, 9);

                switch (j)
                {
                case 0:
                    System.Console.Write(j + ":");
                    break;
                case 1:
                    System.Console.Write(j + ":");
                    break;
                case 2:
                    System.Console.Write(j + ":");
                    break;
                case 3:
                    System.Console.Write(j + ":");
                    break;
                case 4:
                    System.Console.Write(j + ":");
                    break;
                case 5:
                    System.Console.Write(j + ":");
                    break;
                case 6:
                    System.Console.Write(j + ":");
                    break;
                case 7:
                    System.Console.Write(j + ":");
                    break;
                case 8:
                    System.Console.Write(j + ":");
                    break;
                case 9:
                    System.Console.Write(j + ":");
                    break;
                case 10:
                    System.Console.Write(j + ":");
                    break;
                case 11:
                    System.Console.Write(j + ":");
                    break;
                case 12:
                    System.Console.Write(j + ":");
                    break;
                case 13:
                    System.Console.Write(j + ":");
                    break;
                case 14:
                    System.Console.Write(j + ":");
                    break;
                case 15:
                    System.Console.Write(j + ":");
                    break;
                case 16:
                    System.Console.Write(j + ":");
                    break;
                case 17:
                    System.Console.Write(j + ":");
                    break;
                case 18:
                    System.Console.Write(j + ":");
                    break;
                case 19:
                    System.Console.Write(j + ":");
                    break;
                case 20:
                    System.Console.Write(j + ":");
                    break;
                case 21:
                    System.Console.Write(j + ":");
                    break;
                case 22:
                    System.Console.Write(j + ":");
                    break;
                case 23:
                    System.Console.Write(j + ":");
                    break;
                case 24:
                    System.Console.Write(j + ":");
                    break;
                case 25:
                    System.Console.Write(j + ":");
                    break;
                case 26:
                    System.Console.Write(j + ":");
                    break;
                case 27:
                    System.Console.Write(j + ":");
                    break;
                case 28:
                    System.Console.Write(j + ":");
                    break;
                case 29:
                    System.Console.Write(j + ":");
                    break;
                }
            }
            System.Console.WriteLine();
            System.Console.WriteLine("EndIf");
            System.Console.WriteLine(System.DateTime.Now - start);
        }

        private void btnMap_Click(object sender, System.EventArgs e)
        {
            System.Collections.Hashtable map = new System.Collections.Hashtable();

            System.Collections.Hashtable map1 = new System.Collections.Hashtable();
            System.Collections.Hashtable map2 = new System.Collections.Hashtable();
            System.Collections.Hashtable map3 = new System.Collections.Hashtable();

            map1.Add(0, "0");
            map1.Add(1, "1");
            map1.Add(2, "2");
            map1.Add(3, "3");
            map1.Add(4, "4");
            map1.Add(5, "5");
            map1.Add(6, "6");
            map1.Add(7, "7");
            map1.Add(8, "8");
            map1.Add(9, "9");
            map2.Add(10, "10");
            map2.Add(11, "11");
            map2.Add(12, "12");
            map2.Add(13, "13");
            map2.Add(14, "14");
            map2.Add(15, "15");
            map2.Add(16, "16");
            map2.Add(17, "17");
            map2.Add(18, "18");
            map2.Add(19, "19");
            map3.Add(20, "20");
            map3.Add(21, "21");
            map3.Add(22, "22");
            map3.Add(23, "23");
            map3.Add(24, "24");
            map3.Add(25, "25");
            map3.Add(26, "26");
            map3.Add(27, "27");
            map3.Add(28, "28");
            map3.Add(29, "29");

            map.Add(0, map1);
            map.Add(1, map2);
            map.Add(2, map3);

            System.DateTime start = System.DateTime.Now;
            System.Console.WriteLine("StartIf");
            for (int i = 0; i < 100000; i++)
            {
                //System.Console.Write(i + ":");
                System.Random ran = new System.Random();
                int j = 29;//ran.Next(0, 9);
                System.Console.Write(((System.Collections.Hashtable) map[j % 3])[j] + ":");
            }
            System.Console.WriteLine();
            System.Console.WriteLine("EndIf");
            System.Console.WriteLine(System.DateTime.Now - start);
        }

        private void btnList_Click(object sender, System.EventArgs e)
        {
            System.Collections.ArrayList list = new System.Collections.ArrayList();

            System.Collections.ArrayList list1 = new System.Collections.ArrayList();
            System.Collections.ArrayList list2 = new System.Collections.ArrayList();
            System.Collections.ArrayList list3 = new System.Collections.ArrayList();
            list1.Add("0");
            //list1.Add("1");
            //list1.Add("2");
            //list1.Add("3");
            //list1.Add("4");
            //list1.Add("5");
            //list1.Add("6");
            //list1.Add("7");
            //list1.Add("8");
            //list1.Add("9");
            list2.Add("10");
            //list2.Add("11");
            //list2.Add("12");
            //list2.Add("13");
            //list2.Add("14");
            //list2.Add("15");
            //list2.Add("16");
            //list2.Add("17");
            //list2.Add("18");
            //list2.Add("19");
            list3.Add("20");
            list3.Add("21");
            list3.Add("22");
            list3.Add("23");
            list3.Add("24");
            list3.Add("25");
            list3.Add("26");
            list3.Add("27");
            list3.Add("28");
            list3.Add("29");

            list.Add(list1);
            list.Add(list2);
            list.Add(list3);

            System.DateTime start = System.DateTime.Now;
            System.Console.WriteLine("StartIf");
            for (int i = 0; i < 100000; i++)
            {
                //System.Console.Write(i + ":");
                System.Random ran = new System.Random();
                int j = 29;//ran.Next(0, 9);
                System.Console.Write(((System.Collections.ArrayList)list[j % 3])[j / 3] + ":");
            }
            System.Console.WriteLine();
            System.Console.WriteLine("EndIf");
            System.Console.WriteLine(System.DateTime.Now - start);
        }
    }
}
