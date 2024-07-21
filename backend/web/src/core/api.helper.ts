const API = import.meta.env.VITE_API;

export interface Wrapper<T> {
  data: T;
  status: number;
  error?: {
    cause?: string;
    stackTrace: string[];
  };
}

export const getApiUrl = (parts: string[]): string => {
  return [API, ...parts].join('/');
};

export const get = async <T>(url: string | URL): Promise<Wrapper<T>> => {
  const res = await fetch(url);
  return res.json() as unknown as Wrapper<T>;
};
