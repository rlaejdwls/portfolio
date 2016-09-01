using System.Xml.Serialization;

namespace kr.co.bcu.propio.graphic.core.baseflow.model
{
    [XmlRootAttribute(ElementName = "terminal", IsNullable = false)]
    public class FlowTerminalModel
    {
        [XmlAttribute(AttributeName = "start-process")]
        public string StartProcess { get; set; }

        [XmlAttribute(AttributeName = "desc")]
        public string Desc { get; set; }

        [XmlElement(ElementName = "process")]
        public System.Collections.Generic.List<FlowProcessNode> ProcessList { get; set; }

        private System.Collections.Hashtable processMap;

        public T getProcess<T>(string id)
        {
            return (T) processMap[id];
        }

        public void initialize()
        {
            if (processMap == null) { processMap = new System.Collections.Hashtable(); }
            else { processMap.Clear(); }

            if (ProcessList != null)
            {
                foreach (FlowProcessNode process in ProcessList)
                {
                    process.initialize();
                    processMap.Add(process.ProcessID, process);
                }
            }
        }
    }
}
