const API = import.meta.env.VITE_API;

const REQUEST_SETTINGS: RequestInit = {
  headers: {
    Accept: 'application/json',
    'Content-Type': 'application/json',
  },
};

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

const handleResult = async <T>(
  request: () => Promise<Response>
): Promise<T> => {
  const res = (await (await request()).json()) as unknown as Wrapper<unknown>;

  if (res.success) {
    return res.data as T;
  } else {
    throw res.error;
  }
};

const getApiBase = () => {
  if (API) {
    return API;
  }

  return `${window.location.origin}/api`;
};

export const getApiUrl = (parts: string[]): string => {
  return [getApiBase(), ...parts].join('/');
};

export const get = <T>(url: string | URL): Promise<T> => {
  return handleResult<T>(() =>
    fetch(url, { ...REQUEST_SETTINGS, method: 'GET' })
  );
};

export const postWithResult = <T, Result>(
  url: string | URL,
  body: T
): Promise<Result> => {
  return handleResult<Result>(() =>
    fetch(url, {
      ...REQUEST_SETTINGS,
      method: 'POST',
      body: JSON.stringify(body),
    })
  );
};

export const post = <T>(url: string | URL, body: T): Promise<void> => {
  return handleResult<void>(() =>
    fetch(url, {
      ...REQUEST_SETTINGS,
      method: 'POST',
      body: JSON.stringify(body),
    })
  );
};
