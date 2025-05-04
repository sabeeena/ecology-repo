import { useEffect, useState } from 'react';
import { Row, Col, Form, Button } from 'react-bootstrap';
import dayjs from 'dayjs';
import api from '../api/axiosClient';

export default function WeatherFilter({onSearch}) {
    const [cities, setCities] = useState([]);
    const [filter, setFilter] = useState({
        cityId: '',
        from  : dayjs().subtract(7,'day').startOf('hour').toISOString(),
        to    : dayjs().endOf('hour').toISOString()
    });

    /** 1) пытаемся получить геолокацию → /find-city
     2) загружаем справочник городов */
    useEffect(()=>{
        api.get('/reference/cities').then(r=>setCities(r.data));

        if ('geolocation' in navigator)
            navigator.geolocation.getCurrentPosition(({coords})=>{
                api.post('/find-city', {lat:coords.latitude, lon:coords.longitude})
                    .then(r=>setFilter(f=>({...f, cityId:r.data.id})));
            });
    },[]);

    const change=(k,v)=>setFilter({...filter,[k]:v});
    const submit=()=>onSearch(filter);

    return (
        <Form className="mb-3">
            <Row className="g-2 align-items-end">
                <Col md={4}>
                    <Form.Label>Город</Form.Label>
                    <Form.Select
                        value={filter.cityId}
                        onChange={e=>change('cityId', e.target.value)}>
                        <option value="">– выберите город –</option>
                        {cities.map(c=> <option key={c.id} value={c.id}>{c.name}</option>)}
                    </Form.Select>
                </Col>
                <Col md={3}>
                    <Form.Label>Начало периода</Form.Label>
                    <Form.Control type="datetime-local"
                                  defaultValue={filter.from.slice(0,16)}
                                  onChange={e=>change('from', new Date(e.target.value).toISOString())}/>
                </Col>
                <Col md={3}>
                    <Form.Label>Конец периода</Form.Label>
                    <Form.Control type="datetime-local"
                                  defaultValue={filter.to.slice(0,16)}
                                  onChange={e=>change('to', new Date(e.target.value).toISOString())}/>
                </Col>
                <Col md={2}>
                    <Form.Label>&nbsp;</Form.Label>
                    <Button className="w-100 shadow-sm" variant="success" onClick={submit}>
                        Показать
                    </Button>
                </Col>
            </Row>
        </Form>
    );
}