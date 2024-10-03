import { get, getApiUrl, postWithResult } from '../core/api.helper';
import { DecisionTable } from '../models/decision-table.model';
import { GraphModel } from '../models/graph.model';
import { LogicalModel } from '../models/logical-graph.model';

interface ParseObject {
  content: string;
}

interface ParseResult {
  visual: GraphModel;
  logical: LogicalModel;
  decisionTable: DecisionTable;
}

export const fetchInitial = (): Promise<string> => {
  return get<string>(getApiUrl(['graph', 'initial']));
};

export const fetchDummyExample = (): Promise<GraphModel> => {
  return get<GraphModel>(getApiUrl(['graph', 'examples', 'dummy']));
};

export const parseScriptToVisual = (obj: ParseObject): Promise<GraphModel> => {
  return postWithResult<ParseObject, GraphModel>(
    getApiUrl(['graph', 'parse', 'visual']),
    obj
  );
};

export const parseScriptToLogical = (obj: ParseObject): Promise<string[]> => {
  return postWithResult<ParseObject, string[]>(
    getApiUrl(['graph', 'parse', 'logical', 'simple']),
    obj
  );
};

export const parseScript = (obj: ParseObject): Promise<ParseResult> => {
  return postWithResult<ParseObject, ParseResult>(
    getApiUrl(['graph', 'parse', 'all']),
    obj
  );
};
