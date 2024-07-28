import { configureStore } from '@reduxjs/toolkit';
import messagesReducer from './messageSlice';
import contentReducer from './contentSlice';
import warningReducer from './warningSlice';
import graphReducer from './graphSlice';

export const store = configureStore({
  reducer: {
    messages: messagesReducer,
    content: contentReducer,
    warning: warningReducer,
    graph: graphReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
