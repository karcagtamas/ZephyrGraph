import { get, getApiUrl, Wrapper } from '../core/api.helper';
import { GraphModel } from '../models/graph.model';

export const fetchDummyExample = (): Promise<Wrapper<GraphModel>> => {
  return get<GraphModel>(getApiUrl(['graph', 'examples', 'dummy']));
};
