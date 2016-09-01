using System;
using System.Windows.Forms;

namespace kr.co.bcu.propio.util
{
    public class DialogUtil
    {
        public static string getDirectory(string selectedPath)
        {
            try
            {
                using (FolderBrowserDialog dialog = new FolderBrowserDialog())
                {
                    dialog.Description = "폴더를 선택하세요.";
                    dialog.SelectedPath = selectedPath;
                    dialog.ShowNewFolderButton = true;
                    if (dialog.ShowDialog() == DialogResult.OK)
                    {
                        string folder = dialog.SelectedPath;
                        return folder;
                    }
                }
            }
            catch (Exception exc)
            {
                MessageBox.Show("Import failed because " + exc.Message + " , please try again later.");
            }
            return null;
        }

        public static string getOpenFilePath(string initDir, string filter)
        {
            try
            {
                using (OpenFileDialog dialog = new OpenFileDialog())
                {
                    dialog.Title = "파일을 선택하세요.";
                    dialog.InitialDirectory = initDir;
                    dialog.Filter = filter;
                    if (dialog.ShowDialog() == DialogResult.OK)
                    {
                        string path = dialog.FileName;
                        return path;
                    }
                }
            }
            catch (Exception exc)
            {
                MessageBox.Show("Import failed because " + exc.Message + " , please try again later.");
            }
            return null;
        }

        public static string getSaveFilePath(string initDir, string filter)
        {
            try
            {
                using (SaveFileDialog dialog = new SaveFileDialog())
                {
                    dialog.Title = "파일명을 입력하세요.";
                    dialog.InitialDirectory = initDir;
                    dialog.Filter = filter;
                    if (dialog.ShowDialog() == DialogResult.OK)
                    {
                        string path = dialog.FileName;
                        return path;
                    }
                }
            }
            catch (Exception exc)
            {
                MessageBox.Show("Import failed because " + exc.Message + " , please try again later.");
            }
            return null;
        }
    }
}
