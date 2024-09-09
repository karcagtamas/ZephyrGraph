import { createSlice } from '@reduxjs/toolkit';

export type ContentState = {
  content: string;
};

const initialState: ContentState = {
  content: '',
};

export const ContentSlice = createSlice({
  name: 'content',
  initialState,
  reducers: {
    setContent: (state, action: { payload: string; type: string }) => {
      state.content = action.payload;
    },
  },
});

export const { setContent } = ContentSlice.actions;

export default ContentSlice.reducer;
