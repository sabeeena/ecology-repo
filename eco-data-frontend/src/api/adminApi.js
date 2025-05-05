import api from './axiosClient';

export const fetchRoles     = ()          => api.get ('/users/roles');
export const searchUsers    = (filter)    => api.post('/users/info/', filter);
export const createUser     = (body)      => api.post('/users',      body);
export const updateUser     = (id, body)  => api.put (`/users/${id}`,body);
export const deleteUser     = (id)        => api.delete(`/users/${id}`);