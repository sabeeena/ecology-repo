import { Card, Table, OverlayTrigger, Tooltip } from 'react-bootstrap';

export default function AqiInfoCard() {
    const CHEM = {
        SO2: 'Диоксид серы (SO₂) — сжигание угля и нефти; раздражение дыхательных путей, усугубление астмы. <a href="https://www.who.int/news-room/fact-sheets/detail/ambient-(outdoor)-air-quality-and-health" target="_blank" rel="noopener noreferrer">ВОЗ</a>',
        NO2: 'Диоксид азота (NO₂) — выбросы транспорта и ТЭЦ; снижает функцию лёгких, повышает чувствительность к инфекциям. <a href="https://www.epa.gov/no2-pollution/basic-information-about-no2" target="_blank" rel="noopener noreferrer">EPA</a>',
        PM10: 'Частицы ≤10 мкм — пыль, сажа; оседают в верхних дыхательных путях, вызывают воспаление. <a href="https://www.who.int/news-room/fact-sheets/detail/ambient-(outdoor)-air-quality-and-health" target="_blank" rel="noopener noreferrer">ВОЗ</a>',
        PM25: 'Частицы ≤2.5 мкм — копоть, органические соединения; проникают в лёгкие и кровь, увеличивая риск болезней. <a href="https://www.epa.gov/pm-pollution/particulate-matter-pm-basics" target="_blank" rel="noopener noreferrer">EPA</a>',
        O3:  'Озон (O₃) — образуется под УФ на фоне выбросов транспорта; раздражает глаза и дыхание. <a href="https://www.epa.gov/ground-level-ozone-pollution" target="_blank" rel="noopener noreferrer">EPA</a>',
        CO:  'Угарный газ (CO) — продукт неполного сгорания; снижает доставку кислорода в ткани. <a href="https://www.epa.gov/co-pollution/basic-information-about-carbon-monoxide-co-outdoor-air-pollution" target="_blank" rel="noopener noreferrer">EPA</a>'
    };

    const ChemTh = ({ label }) => (
        <th style={{ cursor: 'default' }}>
            {label}&nbsp;
            <OverlayTrigger
                placement="top"
                delay={{ show: 300, hide: 2000 }}
                overlay={
                    <Tooltip>
                        <div dangerouslySetInnerHTML={{ __html: CHEM[label] }} />
                    </Tooltip>
                }
            >
            <span style={{ cursor: 'help', color: '#0d6efd' }}>
                &#9432;
            </span>
            </OverlayTrigger>
        </th>
    );

    const source = (
        <Tooltip id="source-tip">
            Data taken from&nbsp;
            OpenWeather
        </Tooltip>
    );

    return (
        <Card className="mb-4 shadow-sm">
            <Card.Body>
                <Card.Title>Что такое Air Quality Index?</Card.Title>
                <Card.Text className="mb-3">
                    AQI — единая цифра, показывающая общее качество воздуха.
                    <span className="fw-bold"> 1 — «хорошо»</span>,&nbsp;
                    <span className="fw-bold">5 — «очень плохо». </span>
                    Помимо индекса данные содержат концентрации основных загрязнителей:
                    CO, NO, NO<sub>2</sub>, O<sub>3</sub>, SO<sub>2</sub>,
                    NH<sub>3</sub>, а также частиц&nbsp;
                    PM<sub>2.5</sub> и PM<sub>10</sub>.
                </Card.Text>

                <div className="table-responsive">
                    <Table bordered size="sm" className="mb-0 text-center">
                        <thead className="table-light">
                        <tr>
                            <th rowSpan="2" className="align-middle">Качество</th>
                            <th rowSpan="2" className="align-middle">AQI<br/>уровень</th>
                            <th colSpan="6">ПДК* (µg/m³)</th>
                        </tr>
                        <tr>
                            <ChemTh label="SO2" />
                            <ChemTh label="NO2" />
                            <ChemTh label="PM10" />
                            <ChemTh label="PM25" />
                            <ChemTh label="O3" />
                            <ChemTh label="CO" />
                        </tr>
                        </thead>
                        <tbody>
                        {[
                            ['Good', 1, '0-20', '0-40', '0-20', '0-10', '0-60', '0-4 400'],
                            ['Fair', 2, '20-80', '40-70', '20-50', '10-25', '60-100', '4 400-9 400'],
                            ['Moderate', 3, '80-250', '70-150', '50-100', '25-50', '100-140', '9 400-12 400'],
                            ['Poor', 4, '250-350', '150-200', '100-200', '50-75', '140-180', '12 400-15 400'],
                            ['Very poor', 5, '≥350', '≥200', '≥200', '≥75', '≥180', '≥15 400']
                        ].map(row => (
                            <tr key={row[0]}>
                                {row.map((cell, i) => <td key={i}>{cell}</td>)}
                            </tr>
                        ))}
                        </tbody>
                    </Table>

                    <small className="text-muted">
                        * Диапазоны усреднены из методики OpenWeather (
                        <OverlayTrigger overlay={source} placement="top">
                            <span style={{ cursor: 'help' }}>
                                <a
                                    href="https://openweathermap.org/air-pollution-index-levels"
                                    target="_blank"
                                    rel="noopener noreferrer"
                                    className="text-success text-decoration-underline"
                                >
                                    источник
                                </a>
                            </span>
                        </OverlayTrigger>
                        )
                    </small>
                </div>
            </Card.Body>
        </Card>
    );
}
