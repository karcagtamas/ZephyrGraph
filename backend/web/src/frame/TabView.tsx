import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import { SyntheticEvent, useContext, useState } from 'react';
import GraphCodeEditor from '../components/tabs/editor/GraphCodeEditor';
import './TabView.scss';
import { Button } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store/store';
import { EventsContext } from '../core/events.context';
import { clearWarning, setWarning } from '../store/warningSlice';
import Warning from '../components/common/Warning';
import { parseScript } from '../services/graph.service';
import { updateModel } from '../store/graphSlice';
import { setLogical } from '../store/logicalSlice';
import { addMessage } from '../store/messageSlice';
import { MessageType } from '../models/message';
import { localDateTimeConverter } from '../core/date.helper';
import { ErrorData } from '../core/api.helper';
import LogicalPanel from '../components/tabs/logical-panel/LogicalPanel';
import CauseEffectGraph from '../components/tabs/graph/CauseEffectGraph';
import DecisionTable from '../components/tabs/decision-table/DecisionTable';
import { setTable } from '../store/decisionTableSlice';
import MessageBoard from '../components/message-board/MessageBoard';

enum AppTab {
  Editor,
  LogicalResults,
  DecisionTable,
  Graph,
}

type TabPanelProps = {
  children?: React.ReactNode;
  tab: AppTab;
  value: AppTab;
};

const TabPanel = (props: TabPanelProps) => {
  const { children, value, tab } = props;

  return (
    <div
      role="tabpanel"
      className="tab-panel"
      hidden={value !== tab}
      id={`tabpanel-${tab}`}
    >
      {value === tab && (
        <Box className="tab-panel" sx={{ p: 1 }}>
          {children}
        </Box>
      )}
    </div>
  );
};

const TabView = () => {
  const [selectedTab, setSelectedTab] = useState(AppTab.Editor);
  const dispatch = useDispatch();
  const events = useContext(EventsContext);
  const content = useSelector((state: RootState) => state.content.content);
  const warning = useSelector((state: RootState) => state.warning.message);

  const handleTabChange = (_event: SyntheticEvent, value: AppTab) => {
    setSelectedTab(value);
  };

  const handleExecute = () => {
    // setState({ ...state, isExecuting: true });
    parseScript({ content: content })
      .then((res) => {
        dispatch(updateModel(res.visual));
        dispatch(setLogical(res.logical));
        dispatch(setTable(res.decisionTable));
        dispatch(
          addMessage({
            id: new Date().toISOString(),
            content: 'Code is successfully executed.',
            type: MessageType.EXECUTE,
            date: localDateTimeConverter.to(new Date()),
          })
        );
        dispatch(clearWarning());
      })
      .catch((err: ErrorData) => {
        dispatch(updateModel({ nodes: [], edges: [] }));
        dispatch(
          addMessage({
            id: new Date().toISOString(),
            content: `Error during the code execution: ${err.cause}`,
            type: MessageType.ERROR,
            date: localDateTimeConverter.to(new Date()),
          })
        );
        // setState({ ...state, isMessageBoardVisible: true });
        dispatch(
          setWarning('Graph state is invalid because of invalid execution')
        );
      })
      .finally(() => {
        // setState({ ...state, isExecuting: false });
      });
  };

  const handleEditorValueChange = (newValue: string) => {
    if (content !== newValue) {
      dispatch(setWarning('Graph state is obsolete. Please run execution.'));
    }
  };

  const handleReset = () => {
    events.publish('reset', null);
    dispatch(clearWarning());
  };

  return (
    <Box sx={{ bgcolor: 'background.paper' }} className="frame-box">
      <Box sx={{ borderBottom: 1, borderColor: 'divider' }}>
        <Tabs
          value={selectedTab}
          onChange={handleTabChange}
          variant="fullWidth"
        >
          <Tab value={AppTab.Editor} label="Editor"></Tab>
          <Tab
            value={AppTab.LogicalResults}
            label="Logical Results"
            disabled={!!warning}
          ></Tab>
          <Tab
            value={AppTab.DecisionTable}
            label="Decision Table"
            disabled={!!warning}
          ></Tab>
          <Tab value={AppTab.Graph} label="Graph" disabled={!!warning}></Tab>
        </Tabs>
      </Box>
      <TabPanel value={selectedTab} tab={AppTab.Editor}>
        <GraphCodeEditor onChange={handleEditorValueChange} />
        <div style={{ display: 'flex', height: '10px' }}></div>
        <div style={{ display: 'flex' }} className="message-board-box">
          <MessageBoard />
        </div>
        <div style={{ display: 'flex', height: '10px' }}></div>
        <div
          style={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'flex-end',
            gap: '1rem',
          }}
        >
          {warning ? <Warning content={warning} /> : <></>}
          <div style={{ flex: 1 }}></div>
          <Button variant="outlined" onClick={handleReset}>
            Reset
          </Button>
          <Button
            variant="contained"
            disabled={!warning}
            onClick={handleExecute}
          >
            Execute
          </Button>
        </div>
      </TabPanel>
      <TabPanel value={selectedTab} tab={AppTab.LogicalResults}>
        <LogicalPanel />
      </TabPanel>
      <TabPanel value={selectedTab} tab={AppTab.DecisionTable}>
        <DecisionTable />
      </TabPanel>
      <TabPanel value={selectedTab} tab={AppTab.Graph}>
        <CauseEffectGraph />
      </TabPanel>
    </Box>
  );
};

export default TabView;
