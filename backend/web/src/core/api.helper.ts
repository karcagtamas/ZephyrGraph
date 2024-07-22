const API = import.meta.env.VITE_API;

export interface ErrorData {
  cause?: string;
  stackTrace: string[];
}

export interface Wrapper<T> {
  data: T;
  status: number;
  success: boolean;
  error?: ErrorData;
}

const handleResult = async <T>(request: Promise<Response>): Promise<T> => {
  const res = (await request).json() as unknown as Wrapper<unknown>;

  if (res.success) {
    return Promise.resolve(res.data as T);
  } else {
    console.log(res);
    return Promise.reject(res.error);
  }
};

export const getApiUrl = (parts: string[]): string => {
  return [API, ...parts].join('/');
};

export const get = async <T>(url: string | URL): Promise<Wrapper<T>> => {
  const res = await fetch(url, { method: 'GET' });
  return res.json() as unknown as Wrapper<T>;
};

export const postWithResult = async <T, Result>(
  url: string | URL,
  body: T
): Promise<Wrapper<Result>> => {
  return handleResult(
    fetch(url, { method: 'POST', body: JSON.stringify(body) })
  );
};

export const post = async <T>(url: string | URL, body: T): Promise<void> => {
  return handleResult(
    fetch(url, { method: 'POST', body: JSON.stringify(body) })
  );
};
