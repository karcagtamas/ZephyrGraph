import { createSlice } from '@reduxjs/toolkit';

export type ContentState = {
  content: string;
};

const initialState: ContentState = {
  content: '',
};

export const MessagesSlice = createSlice({
  name: 'messages',
  initialState,
  reducers: {
    setContent: (state, action: { payload: string; type: string }) => {
      state.content = action.payload;
    },
  },
});

export const { setContent } = MessagesSlice.actions;

export default MessagesSlice.reducer;
