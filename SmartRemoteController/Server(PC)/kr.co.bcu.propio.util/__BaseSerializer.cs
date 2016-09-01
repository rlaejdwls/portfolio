using System;
using System.IO;
using System.Xml.Serialization;

namespace kr.co.bcu.propio.util
{
    public class __BaseSerializer
    {
        public static bool Write<T>(T settings, string path)
        {
            FileStream parameter = null;
            try
            {
                parameter = new FileStream(path, FileMode.Create, FileAccess.Write);
                return Write<T>(settings, parameter);
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return false;
            }
            finally
            {
                if (parameter != null) parameter.Close();
            }
        }
        public static bool Write<T>(T settings, Stream stream)
        {
            StreamWriter writer = null;
            try
            {
                XmlSerializer x = new XmlSerializer(settings.GetType());
                writer = new StreamWriter(stream);

                x.Serialize(writer, settings);

                return true;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.ToString());
                return false;
            }
        }

        public static T Read<T>(string path)
        {
            FileStream parameter = null;
            try
            {
                parameter = new FileStream(path, FileMode.Open, FileAccess.Read);
                return Read<T>(parameter); ;
            }
            catch (Exception e)
            {
                Console.WriteLine(e.Message);
                return default(T);
            }
            finally
            {
                if (parameter != null) parameter.Close();
            }
        }
        public static T Read<T>(Stream stream)
        {
            T settings = default(T);

            StreamReader reader = null;
            try
            {
                XmlSerializer x = new XmlSerializer(typeof(T));
                reader = new StreamReader(stream);
                settings = (T)x.Deserialize(reader);

                return settings;
            }
            catch (Exception e)
            {
                Console.Write(e.Message);
                return settings;
            }
            finally
            {
                if (stream != null) stream.Close();
            }
        }
    }
}
