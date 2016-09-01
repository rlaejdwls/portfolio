namespace kr.co.bcu.propio.win32api.define.model
{
    [System.Xml.Serialization.XmlRoot]
    public class Define
    {
        [System.Xml.Serialization.XmlElement]
        public System.Collections.Generic.List<Groups> Groups { get; set; }

        private System.Collections.Hashtable value = new System.Collections.Hashtable();

        public byte GetValue(string key)
        {
            if (value.Count == 0)
            {
                foreach (Groups group in Groups)
                {
                    foreach (Node node in group.Node)
                    {
                        try
                        {
                            value.Add(node.Const, System.Convert.ToByte(node.Value, 16));
                        }
                        catch (System.Exception e)
                        {
                            System.Console.WriteLine(e.Message);
                        }
                    }
                }
            }

            try
            {
                if (key.Equals("")) { return 0; }
                if (value.ContainsKey(key)) { return (byte)value[key]; }
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
    }
}
