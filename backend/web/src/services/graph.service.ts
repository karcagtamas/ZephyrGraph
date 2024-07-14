import { get, getApiUrl } from '../core/api.helper';
import { GraphModel } from '../models/graph.model';

export const fetchDummyExample = (): Promise<GraphModel> => {
  return get<GraphModel>(getApiUrl(['graph', 'examples', 'dummy']));
};
