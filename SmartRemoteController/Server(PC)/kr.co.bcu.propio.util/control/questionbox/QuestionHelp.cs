using kr.co.bcu.propio.util.control.questionbox.model;

namespace kr.co.bcu.propio.util.control.questionbox
{
    public partial class QuestionHelp : System.Windows.Forms.Form
    {
        public QuestionHelp(string helpPath)
        {
            this.helpPath = helpPath;

            InitializeComponent();
            initialize();
        }

        private HelpModel helpModel;
        private string helpPath;

        private void QuestionHelp_Load(object sender, System.EventArgs e)
        {
            controlSize();

            this.Location = new System.Drawing.Point(helpModel.X, helpModel.Y);
            this.Size = new System.Drawing.Size(helpModel.Width == 0 ? this.Size.Width : helpModel.Width, 
                helpModel.Height == 0 ? this.Size.Height : helpModel.Height);
        }
        private void QuestionHelp_FormClosed(object sender, System.Windows.Forms.FormClosedEventArgs e)
        {
            //display config finalize
            if (this.Location.X != -32000 && this.Location.Y != -32000)
            {
                helpModel.X = this.Location.X;
                helpModel.Y = this.Location.Y;
                helpModel.Width = this.Size.Width;
                helpModel.Height = this.Size.Height;
            }

            __BaseSerializer.Write<HelpModel>(helpModel, helpPath);
        }
        private void QuestionHelp_Resize(object sender, System.EventArgs e)
        {
            controlSize();
        }
        private void openToolStripMenuItem_Click(object sender, System.EventArgs e)
        {

        }
        private void saveToolStripMenuItem_Click(object sender, System.EventArgs e)
        {

        }
        private void saveasToolStripMenuItem_Click(object sender, System.EventArgs e)
        {

        }
        private void unlockToolStripMenuItem_Click(object sender, System.EventArgs e)
        {

        }
        private void newPageToolStripMenuItem_Click(object sender, System.EventArgs e)
        {

        }
        private void deletePageToolStripMenuItem_Click(object sender, System.EventArgs e)
        {

        }
        private void closeToolStripMenuItem_Click(object sender, System.EventArgs e)
        {
            this.Close();
        }
        private void btnPrev_Click(object sender, System.EventArgs e)
        {
            btnNext.Enabled = true;
            helpModel.SelectPage--;
            printText();
        }
        private void btnNext_Click(object sender, System.EventArgs e)
        {
            btnPrev.Enabled = true;
            helpModel.SelectPage++;
            printText();
        }
        private void btnClose_Click(object sender, System.EventArgs e)
        {
            this.Close();
        }

        private void initialize()
        {
            try
            {
                helpModel = __BaseSerializer.Read<HelpModel>(helpPath);

                txtHelp.ReadOnly = helpModel.IsLock;

                if (helpModel != null)
                {
                    printText();
                }
                else
                {
                    System.Windows.Forms.MessageBox.Show("내용을 불러오는데 실패했습니다.", "알림");
                    this.Close();
                }
            }
            catch (System.Exception e)
            {
                System.Windows.Forms.MessageBox.Show("설정 파일을 확인하세요.\n ::" + e.Message, "알림");
                this.Close();
            }
        }
        private void printText()
        {
            foreach (PageNode node in helpModel.Page)
            {
                if (node.Num == helpModel.SelectPage)
                {
                    txtHelp.Text = node.Text;
                    lblSelectPage.Text = (node.Num + 1).ToString();
                }
            }
            if (helpModel.SelectPage == 0) btnPrev.Enabled = false;
            if (helpModel.SelectPage == helpModel.Page.Count - 1) btnNext.Enabled = false;
            lblPageCount.Text = helpModel.Page.Count.ToString();

            __BaseSerializer.Write<HelpModel>(helpModel, helpPath);
        }
        private void controlSize()
        {
            txtHelp.Width = this.ClientRectangle.Size.Width;
            txtHelp.Height = this.ClientRectangle.Size.Height - menuStrip.Height - btnClose.Height - 10;

            btnClose.Location = new System.Drawing.Point(this.ClientRectangle.Size.Width - btnClose.Size.Width - 5,
                txtHelp.Location.Y + txtHelp.Size.Height + 5);
            btnNext.Location = new System.Drawing.Point(btnClose.Location.X - btnNext.Size.Width - 5,
                txtHelp.Location.Y + txtHelp.Size.Height + 5);
            btnPrev.Location = new System.Drawing.Point(btnNext.Location.X - btnPrev.Size.Width - 5,
                txtHelp.Location.Y + txtHelp.Size.Height + 5);

            lblPageCount.Location = new System.Drawing.Point(lblPageCount.Location.X, 
                btnClose.Location.Y + (btnClose.Size.Height / 2) - (lblPageCount.Size.Height / 2));
            lblSelectPage.Location = new System.Drawing.Point(lblSelectPage.Location.X,
                btnClose.Location.Y + (btnClose.Size.Height / 2) - (lblSelectPage.Size.Height / 2));
            lblSlash.Location = new System.Drawing.Point(lblSlash.Location.X,
                btnClose.Location.Y + (btnClose.Size.Height / 2) - (lblSlash.Size.Height / 2));
        }
    }
}
