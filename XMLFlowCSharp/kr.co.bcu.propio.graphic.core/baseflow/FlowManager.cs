using kr.co.bcu.propio.graphic.core.baseflow.model;
using kr.co.bcu.propio.util;
using kr.co.bcu.propio.util.config;

namespace kr.co.bcu.propio.graphic.core.baseflow
{
    public class FlowManager
    {
        private FlowTerminalModel thisTerminal;
        private FlowProcessNode thisProcess;

        public __BaseProcess initialize()
        {
            return nextTerminal("\\" + __BaseConfig.getConfig("CoreConfig")["StartFlowBinder"].ToString() + ".xml");
        }

        public __BaseProcess getProcess(string processId, string assemName)
        {
            try
            {
                thisProcess = thisTerminal.getProcess<FlowProcessNode>(processId);
                string processPath = thisProcess.ProcessPath;

                System.Reflection.Assembly assembly = System.Reflection.Assembly.Load(assemName);
                if (assembly != null)
                {
                    System.Type type = assembly.GetType(processPath);
                    return (__BaseProcess)System.Activator.CreateInstance(type);
                }
            }
            catch (System.Exception e)
            {
                System.Windows.Forms.MessageBox.Show(e.ToString());
            }
            return null;
        }

        public FlowTerminalModel getTerminal(string terminalPath)
        {
            try
            {
                FlowTerminalModel terminal = __BaseSerializer.Read<FlowTerminalModel>
                    (__BaseConfig.getConfig("CoreConfig")["Workspace"].ToString() + terminalPath);
                terminal.initialize();

                return terminal;
            }
            catch (System.Exception e)
            {
               System.Windows.Forms.MessageBox.Show(e.ToString());
            }
            return null;
        }

        public __BaseProcess nextFlowline(string flowlineId)
        {
            FlowFlowlineNode flowline = thisProcess.getFlowline<FlowFlowlineNode>(flowlineId);

            if (flowline.NextTerminal != null)
            {
                return nextTerminal(flowline.NextTerminal + ".xml");
            }
            else if (flowline.NextProcess != null)
            {
                return nextProcess(flowline.NextProcess);
            }
            
            return null;
        }

        public __BaseProcess nextProcess(string processId)
        {
            return getProcess(processId, System.Reflection.Assembly.GetEntryAssembly().GetName().Name);
        }

        public __BaseProcess nextTerminal(string terminalPath)
        {
            thisTerminal = getTerminal(terminalPath);
            if (thisTerminal != null)
            {
                return getProcess(thisTerminal.StartProcess, System.Reflection.Assembly.GetEntryAssembly().GetName().Name);
            }
            return null;
        }
    }
}
