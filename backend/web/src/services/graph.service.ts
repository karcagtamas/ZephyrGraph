import { get, getApiUrl, postWithResult, Wrapper } from '../core/api.helper';
import { GraphModel } from '../models/graph.model';

export const fetchInitial = (): Promise<Wrapper<string>> => {
  return get<string>(getApiUrl(['graph', 'initial']));
};

export const fetchDummyExample = (): Promise<Wrapper<GraphModel>> => {
  return get<GraphModel>(getApiUrl(['graph', 'examples', 'dummy']));
};

export const parseScript = (content: string): Promise<Wrapper<GraphModel>> => {
  return postWithResult<string, GraphModel>(
    getApiUrl(['graph', 'parse']),
    content
  );
};
