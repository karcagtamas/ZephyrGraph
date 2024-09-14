import { get, getApiUrl, postWithResult } from '../core/api.helper';
import { GraphModel } from '../models/graph.model';

interface ParseObject {
  content: string;
}

export const fetchInitial = (): Promise<string> => {
  return get<string>(getApiUrl(['graph', 'initial']));
};

export const fetchDummyExample = (): Promise<GraphModel> => {
  return get<GraphModel>(getApiUrl(['graph', 'examples', 'dummy']));
};

export const parseScript = (obj: ParseObject): Promise<GraphModel> => {
  return postWithResult<ParseObject, GraphModel>(
    getApiUrl(['graph', 'parse', 'visual']),
    obj
  );
};
