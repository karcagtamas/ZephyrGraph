import { configureStore } from '@reduxjs/toolkit';
import messagesReducer from './messageSlice';
import contentReducer from './contentSlice';

export const store = configureStore({
  reducer: {
    messages: messagesReducer,
    content: contentReducer,
  },
});

export type RootState = ReturnType<typeof store.getState>;
export type AppDispatch = typeof store.dispatch;
