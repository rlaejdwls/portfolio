using System.Collections;
using System.Collections.Generic;
using System.IO;
using System.Windows.Forms;
using System.Xml;
using kr.co.bcu.propio.util.config.model;

namespace kr.co.bcu.propio.util.config
{
    public class __BaseConfig
    {
        private static __BaseConfig config;
        private System.Collections.Hashtable models;

        public static ConfigModel getConfig()
        {
            return getConfig("PropioConfig");
        }
        
        public static ConfigModel getConfig(string configName)
        {
            return getConfig(configName, new FileStream(Application.StartupPath + "\\config\\" + configName + ".xml", FileMode.Open, FileAccess.Read));
        }
        public static ConfigModel getConfig(string configPath, string configName)
        {
            return getConfig(configName, new FileStream(configPath + "\\" + configName + ".xml", FileMode.Open, FileAccess.Read));
        }
        public static ConfigModel getConfig(string key, Stream configStream)
        {
            if (config == null)
            {
                config = new __BaseConfig(/*key, configStream*/);
            }
            
            if (!config.models.ContainsKey(key))
            {
                config.models.Add(key, config.read(configStream));
            }
            else
            {
                if (configStream != null) configStream.Close();
            }

            return (ConfigModel) config.models[key];
        }

        public static void setConfig(ConfigModel model)
        {
            setConfig(model, "PropioConfig");
        }

        public static void setConfig(ConfigModel model, string configName)
        {
            getConfig(configName);
            config.write(model, Application.StartupPath + "\\config\\" + configName+ ".xml");
        }

        private __BaseConfig(/*string key, Stream configStream*/)
        {
            models = new System.Collections.Hashtable();
        }

        public ConfigModel read(Stream configStream)
        {
            ConfigModel model = new ConfigModel();
            kr.co.bcu.propio.util.config.model.DynamicXmlModel xmlModel =
                __BaseSerializer.Read<DynamicXmlModel>(configStream);

            if (xmlModel != null)
            {
                foreach (XmlNode node in xmlModel.obj)
                {
                    parse(node, model);
                    //model.Add(node.Name, node.InnerText);
                }
            }

            return model;
        }

        public void parse(XmlNode node, Hashtable map)
        {
            Hashtable childMap = new Hashtable();
            foreach (XmlNode child in node.ChildNodes)
            {
                if (child.NodeType == XmlNodeType.Text)
                {
                    if (map.ContainsKey(child.ParentNode.Name))
                    {
                        if (map[child.ParentNode.Name] is List<object>)
                        {
                            List<object> objList = (List<object>)map[child.ParentNode.Name];
                            objList.Add(child.Value);
                        }
                        else
                        {
                            List<object> objList = new List<object>();
                            objList.Add(map[child.ParentNode.Name]);
                            objList.Add(child.Value);
                            map.Remove(child.ParentNode.Name);
                            map.Add(child.ParentNode.Name, objList);
                        }
                    }
                    else
                    {
                        map.Add(child.ParentNode.Name, child.Value);
                    }
                }
                else if (child.NodeType == XmlNodeType.Element)
                {
                    parse(child, childMap);
                }
            }
            if (childMap.Count != 0)
            {
                if (map.ContainsKey(node.Name))
                {
                    if (map[node.Name] is List<object>)
                    {
                        List<object> objList = (List<object>)map[node.Name];
                        objList.Add(childMap);
                    }
                    else
                    {
                        List<object> objList = new List<object>();
                        objList.Add(map[node.Name]);
                        objList.Add(childMap);
                        map.Remove(node.Name);
                        map.Add(node.Name, objList);
                    }
                }
                else
                {
                    map.Add(node.Name, childMap);
                }
            }
        }

        public void write(ConfigModel model, string path)
        {
            IDictionaryEnumerator ide = model.GetEnumerator();
            DynamicXmlModel dynamicModel = new DynamicXmlModel(new List<XmlNode>());
            XmlDocument doc = new XmlDocument();
            XmlNode rootNode = doc.CreateNode(XmlNodeType.Element, "PropioConfig", null);

            create(rootNode, doc, ide);
            foreach (XmlNode addNode in rootNode.ChildNodes) { dynamicModel.obj.Add(addNode); }

            __BaseSerializer.Write<DynamicXmlModel>(dynamicModel, path);
        }

        public void create(XmlNode node, XmlDocument doc, IDictionaryEnumerator ide)
        {
            while (ide.MoveNext())
            {
                XmlNode child = null;

                if (ide.Value is List<object>)
                {
                    List<object> objList = (List<object>)ide.Value;

                    foreach (object obj in objList)
                    {
                        child = doc.CreateNode(XmlNodeType.Element, ide.Key.ToString(), null);
                        if      (obj is Hashtable)  { create(child, doc, ((Hashtable)obj).GetEnumerator()); }
                        else if (obj is string)     { child.InnerText = obj.ToString(); }
                        node.AppendChild(child);
                    }
                }
                else if (ide.Value is Hashtable)
                {
                    child = doc.CreateNode(XmlNodeType.Element, ide.Key.ToString(), null);
                    create(child, doc, ((Hashtable)ide.Value).GetEnumerator());
                    node.AppendChild(child);
                }
                else
                {
                    child = doc.CreateNode(XmlNodeType.Element, ide.Key.ToString(), null);
                    child.InnerText = ide.Value.ToString();
                    node.AppendChild(child);
                }
            }
        }
    }
}
