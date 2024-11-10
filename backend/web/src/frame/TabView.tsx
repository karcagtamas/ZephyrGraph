import Box from '@mui/material/Box';
import Tab from '@mui/material/Tab';
import Tabs from '@mui/material/Tabs';
import { SyntheticEvent, useContext, useState } from 'react';
import GraphCodeEditor from '../components/tabs/editor/GraphCodeEditor';
import './TabView.scss';
import { Alert, Button, Card, CircularProgress } from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { RootState } from '../store/store';
import { EventsContext } from '../core/events.context';
import { clearWarning, setWarning } from '../store/warningSlice';
import { parseScript } from '../services/graph.service';
import { updateModel } from '../store/graphSlice';
import { setLogical } from '../store/logicalSlice';
import { addMessage } from '../store/messageSlice';
import { message, MessageType } from '../models/message';
import { ErrorData } from '../core/api.helper';
import LogicalPanel from '../components/tabs/logical-panel/LogicalPanel';
import CauseEffectGraph from '../components/tabs/graph/CauseEffectGraph';
import DecisionTable from '../components/tabs/decision-table/DecisionTable';
import { setTable } from '../store/decisionTableSlice';
import MessageBoard from '../components/message-board/MessageBoard';
import Export from '../components/tabs/export/Export';
import { setExport } from '../store/exportSlice';
import { addError, addInfo, addSuccess } from '../store/snackbarSlice';

enum AppTab {
  Editor,
  LogicalResults,
  DecisionTable,
  Export,
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
  const [isExecuting, setIsExecuting] = useState(false);
  const dispatch = useDispatch();
  const events = useContext(EventsContext);
  const content = useSelector((state: RootState) => state.content.content);
  const warning = useSelector((state: RootState) => state.warning.message);

  const handleTabChange = (_event: SyntheticEvent, value: AppTab) => {
    setSelectedTab(value);
  };

  const handleExecute = () => {
    setIsExecuting(true);
    parseScript({ content: content })
      .then((res) => {
        dispatch(updateModel(res.visual));
        dispatch(setLogical(res.logical));
        dispatch(setTable(res.decisionTable));
        dispatch(setExport(res.export));
        dispatch(
          addMessage(
            message('Code is successfully executed.', MessageType.EXECUTE)
          )
        );
        dispatch(clearWarning());
        dispatch(addSuccess('Code has been executed successfully.'));
      })
      .catch((err: ErrorData) => {
        dispatch(updateModel({ nodes: [], edges: [] }));
        dispatch(
          addMessage(
            message(
              `Error during the code execution: ${err.cause}`,
              MessageType.ERROR,
              err.stackTrace.join('\n')
            )
          )
        );
        dispatch(
          setWarning('Graph state is invalid because of invalid execution')
        );
        dispatch(
          addError(
            'Error during the execution. Check the Messages for further details.'
          )
        );
      })
      .finally(() => setIsExecuting(false));
  };

  const handleEditorValueChange = (newValue: string) => {
    if (content !== newValue) {
      dispatch(setWarning('Graph state is obsolete. Please run execution.'));
    }
  };

  const handleReset = () => {
    events.publish('reset', null);
    dispatch(clearWarning());
    dispatch(
      addMessage(message('Editor content is reseted', MessageType.INFO))
    );
    dispatch(addInfo('The editor has been reseted.'));
  };

  const spacer = <div style={{ display: 'flex', height: '10px' }}></div>;

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
          <Tab value={AppTab.Export} label="Export" disabled={!!warning}></Tab>
          <Tab value={AppTab.Graph} label="Graph" disabled={!!warning}></Tab>
        </Tabs>
      </Box>
      <TabPanel value={selectedTab} tab={AppTab.Editor}>
        <Card
          style={{
            display: 'flex',
            flex: 1,
            overflow: 'hidden',
            padding: '10px',
          }}
        >
          <GraphCodeEditor onChange={handleEditorValueChange} />
        </Card>
        {spacer}
        <Card style={{ display: 'flex' }} className="message-board-box">
          <MessageBoard caption="Messages" />
        </Card>
        {spacer}
        <div
          style={{
            display: 'flex',
            flexDirection: 'row',
            justifyContent: 'flex-end',
            gap: '1rem',
          }}
        >
          {warning ? <Alert severity="warning">{warning}</Alert> : <></>}
          <div style={{ flex: 1 }}></div>
          <Button
            variant="outlined"
            onClick={handleReset}
            disabled={isExecuting}
          >
            Reset
          </Button>
          <Button
            variant="contained"
            disabled={!warning || isExecuting}
            onClick={handleExecute}
          >
            {isExecuting ? (
              <CircularProgress size={23} />
            ) : (
              <span>Execute</span>
            )}
          </Button>
        </div>
      </TabPanel>
      <TabPanel value={selectedTab} tab={AppTab.LogicalResults}>
        <LogicalPanel />
      </TabPanel>
      <TabPanel value={selectedTab} tab={AppTab.DecisionTable}>
        <DecisionTable />
      </TabPanel>
      <TabPanel value={selectedTab} tab={AppTab.Export}>
        <Export />
      </TabPanel>
      <TabPanel value={selectedTab} tab={AppTab.Graph}>
        <CauseEffectGraph />
      </TabPanel>
    </Box>
  );
};

export default TabView;
