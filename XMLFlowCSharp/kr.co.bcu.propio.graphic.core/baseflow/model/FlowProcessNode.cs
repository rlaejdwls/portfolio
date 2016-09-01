using System.Xml.Serialization;

namespace kr.co.bcu.propio.graphic.core.baseflow.model
{
    public class FlowProcessNode
    {
        [XmlAttribute(AttributeName = "id")]
        public string ProcessID { get; set; }

        [XmlAttribute(AttributeName = "process-path")]
        public string ProcessPath { get; set; }

        [XmlAttribute(AttributeName = "desc")]
        public string Desc { get; set; }

        [XmlElement(ElementName = "flowline")]
        public System.Collections.Generic.List<FlowFlowlineNode> FlowlineList { get; set; }

        private System.Collections.Hashtable flowlineMap;

        public T getFlowline<T>(string id)
        {
            return (T) flowlineMap[id];
        }

        public void initialize()
        {
            if (flowlineMap == null) { flowlineMap = new System.Collections.Hashtable(); }
            else { flowlineMap.Clear(); }

            if (FlowlineList != null)
            {
                foreach (FlowFlowlineNode flowline in FlowlineList)
                {
                    flowline.initialize();
                    flowlineMap.Add(flowline.FlowlineID, flowline);
                }
            }
        }
    }
}
