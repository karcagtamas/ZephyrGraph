import { createSlice } from '@reduxjs/toolkit';
import { LogicalModel } from '../models/logical-graph.model';

export type LogicalState = LogicalModel;

const initialState: LogicalState = {
  final: { key: 'dummy', definitions: [] },
  prevSteps: [],
};

export const LogicalSlice = createSlice({
  name: 'logical',
  initialState,
  reducers: {
    setLogical: (state, action: { payload: LogicalModel; type: string }) => {
      state.final = action.payload.final;
      state.prevSteps = action.payload.prevSteps;
    },
  },
});

export const { setLogical } = LogicalSlice.actions;

export default LogicalSlice.reducer;
