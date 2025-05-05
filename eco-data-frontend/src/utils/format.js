import dayjs from 'dayjs';

export const fmtDate = d => d ? dayjs(d).format('DD.MM.YYYY HH:mm') : '';
