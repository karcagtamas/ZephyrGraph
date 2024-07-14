const API = import.meta.env.VITE_API;

export const getApiUrl = (parts: string[]): string => {
  return [API, ...parts].join('/');
};

export const get = async <T>(url: string | URL): Promise<T> => {
  const res = await fetch(url);
  return res.json() as T;
};
