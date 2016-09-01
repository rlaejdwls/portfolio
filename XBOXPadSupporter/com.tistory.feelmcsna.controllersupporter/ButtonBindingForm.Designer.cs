namespace com.tistory.feelmcsna.controllersupporter
{
    partial class ButtonBindingForm
    {
        /// <summary>
        /// 필수 디자이너 변수입니다.
        /// </summary>
        private System.ComponentModel.IContainer components = null;

        /// <summary>
        /// 사용 중인 모든 리소스를 정리합니다.
        /// </summary>
        /// <param name="disposing">관리되는 리소스를 삭제해야 하면 true이고, 그렇지 않으면 false입니다.</param>
        protected override void Dispose(bool disposing)
        {
            if (disposing && (components != null))
            {
                components.Dispose();
            }
            base.Dispose(disposing);
        }

        #region Windows Form 디자이너에서 생성한 코드

        /// <summary>
        /// 디자이너 지원에 필요한 메서드입니다.
        /// 이 메서드의 내용을 코드 편집기로 수정하지 마십시오.
        /// </summary>
        private void InitializeComponent()
        {
            this.LblBtnValue = new System.Windows.Forms.Label();
            this.BtnOK = new System.Windows.Forms.Button();
            this.SuspendLayout();
            // 
            // LblBtnValue
            // 
            this.LblBtnValue.AutoSize = true;
            this.LblBtnValue.Location = new System.Drawing.Point(56, 9);
            this.LblBtnValue.Name = "LblBtnValue";
            this.LblBtnValue.Size = new System.Drawing.Size(38, 12);
            this.LblBtnValue.TabIndex = 0;
            this.LblBtnValue.Text = "label1";
            // 
            // BtnOK
            // 
            this.BtnOK.FlatStyle = System.Windows.Forms.FlatStyle.Flat;
            this.BtnOK.Location = new System.Drawing.Point(134, 4);
            this.BtnOK.Name = "BtnOK";
            this.BtnOK.Size = new System.Drawing.Size(78, 23);
            this.BtnOK.TabIndex = 1;
            this.BtnOK.Text = "적용";
            this.BtnOK.UseVisualStyleBackColor = true;
            this.BtnOK.Click += new System.EventHandler(this.BtnOK_Click);
            this.BtnOK.PreviewKeyDown += new System.Windows.Forms.PreviewKeyDownEventHandler(this.BtnOK_PreviewKeyDown);
            // 
            // ButtonBindingForm
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(224, 33);
            this.Controls.Add(this.BtnOK);
            this.Controls.Add(this.LblBtnValue);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.FixedToolWindow;
            this.Name = "ButtonBindingForm";
            this.StartPosition = System.Windows.Forms.FormStartPosition.CenterParent;
            this.Text = "Form1";
            this.FormClosing += new System.Windows.Forms.FormClosingEventHandler(this.ButtonBindingForm_FormClosing);
            this.Load += new System.EventHandler(this.ButtonBindingForm_Load);
            this.KeyDown += new System.Windows.Forms.KeyEventHandler(this.ButtonBindingForm_KeyDown);
            this.KeyUp += new System.Windows.Forms.KeyEventHandler(this.ButtonBindingForm_KeyUp);
            this.ResumeLayout(false);
            this.PerformLayout();

        }

        #endregion

        private System.Windows.Forms.Label LblBtnValue;
        private System.Windows.Forms.Button BtnOK;
    }
}