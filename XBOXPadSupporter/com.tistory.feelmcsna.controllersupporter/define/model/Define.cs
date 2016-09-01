namespace com.tistory.feelmcsna.controllersupporter.define.model
{
    [System.Xml.Serialization.XmlRoot]
    public class Define
    {
        [System.Xml.Serialization.XmlElement]
        public System.Collections.Generic.List<Groups> Groups { get; set; }

        private System.Collections.Hashtable define = new System.Collections.Hashtable();

        public byte GetValue(string key)
        {
            if (define.Count == 0)
            {
                Initialize();
            }

            try
            {
                if (key.Equals("")) { return 0; }
                if (define.ContainsKey(key)) { return (byte)define[key]; }
                else
                {
                    return (byte)key.ToCharArray()[0];
                }
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine(e.Message);
                return 0;
            }
        }
        public string GetKey(byte value)
        {
            if (define.Count == 0)
            {
                Initialize();
            }

            try
            {
                if (value == 0) { return null; }
                foreach (System.Collections.DictionaryEntry entry in define)
                {
                    if (value == (byte)entry.Value)
                    {
                        return entry.Key.ToString();
                    }
                }
                return ((char)value).ToString();
            }
            catch (System.Exception e)
            {
                System.Console.WriteLine(e.Message);
                return null;
            }
        }
        private void Initialize()
        {
            foreach (Groups group in Groups)
            {
                foreach (Node node in group.Node)
                {
                    try
                    {
                        define.Add(node.Const, System.Convert.ToByte(node.Value, 16));
                    }
                    catch (System.Exception e)
                    {
                        System.Console.WriteLine(e.Message);
                    }
                }
            }
        }
    }
}
