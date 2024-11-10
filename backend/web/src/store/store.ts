import { configureStore } from '@reduxjs/toolkit';
import messagesReducer from './messageSlice';
import contentReducer from './contentSlice';
import warningReducer from './warningSlice';
import graphReducer from './graphSlice';
import logicalReducer from './logicalSlice';
import decisionTableReducer from './decisionTableSlice';
import exportReducer from './exportSlice';
import snackbarReducer from './snackbarSlice';

export const store = configureStore({
  reducer: {
    messages: messagesReducer,
    content: contentReducer,
    warning: warningReducer,
    graph: graphReducer,
    logical: logicalReducer,
    decisionTable: decisionTableReducer,
    export: exportReducer,
    snackbar: snackbarReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
