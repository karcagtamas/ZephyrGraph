import { Message } from '../models/message';
import { createSlice } from '@reduxjs/toolkit';

export type MessagesState = {
  messages: Message[];
};

const initialState: MessagesState = {
  messages: [],
};

export const MessagesSlice = createSlice({
  name: 'messages',
  initialState,
  reducers: {
    addMessage: (state, action: { payload: Message; type: string }) => {
      state.messages = [...state.messages, action.payload];
    },
  },
});

export const { addMessage } = MessagesSlice.actions;

export default MessagesSlice.reducer;
