﻿namespace kr.co.bcu.propio.graphic.core
{
    partial class __BaseMain
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
            this.SuspendLayout();
            // 
            // __BaseMain
            // 
            this.AutoScaleDimensions = new System.Drawing.SizeF(7F, 12F);
            this.AutoScaleMode = System.Windows.Forms.AutoScaleMode.Font;
            this.ClientSize = new System.Drawing.Size(1920, 1200);
            this.FormBorderStyle = System.Windows.Forms.FormBorderStyle.None;
            this.Name = "__BaseMain";
            this.Text = "__BaseMain";
            this.Load += new System.EventHandler(this.@__BaseMain_Load);
            this.ResizeBegin += new System.EventHandler(this.@__BaseMain_ResizeBegin);
            this.ResizeEnd += new System.EventHandler(this.@__BaseMain_ResizeEnd);
            this.MouseClick += new System.Windows.Forms.MouseEventHandler(this.@__BaseMain_MouseClick);
            this.MouseDoubleClick += new System.Windows.Forms.MouseEventHandler(this.@__BaseMain_MouseDoubleClick);
            this.MouseDown += new System.Windows.Forms.MouseEventHandler(this.@__BaseMain_MouseDown);
            this.MouseMove += new System.Windows.Forms.MouseEventHandler(this.@__BaseMain_MouseMove);
            this.MouseUp += new System.Windows.Forms.MouseEventHandler(this.@__BaseMain_MouseUp);
            this.Resize += new System.EventHandler(this.@__BaseMain_Resize);
            this.ResumeLayout(false);

        }

        #endregion
    }
}