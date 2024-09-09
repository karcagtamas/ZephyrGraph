import { createSlice } from '@reduxjs/toolkit';
import { GraphModel } from '../models/graph.model';

export type GraphState = {
  model: GraphModel | null;
};

const initialState: GraphState = {
  model: null,
};

export const GraphSlice = createSlice({
  name: 'graph',
  initialState,
  reducers: {
    updateModel: (state, action: { payload: GraphModel; type: string }) => {
      state.model = action.payload;
    },
  },
});

export const { updateModel } = GraphSlice.actions;

export default GraphSlice.reducer;
