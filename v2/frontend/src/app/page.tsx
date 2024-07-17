'use client';

import PersonalityTestForm from "@/components/PersonalityTestForm";
import PersonalityTestReport from "@/components/PersonalityTestReport";
import { Button, Spin } from "antd";
import { useState } from "react";


export default function Home() {
  const [loading, setLoading] = useState(false);
  const [evaluated, setEvaluated] = useState(false);
  const [evaluations, setEvaluations] = useState<IEvaluation[]>([]);

  const handleFinish = async (values: any) => {
    const linePoints: any = {}
    for (const property in values) {
      linePoints[property] = Number(values[property]);
    }

    setLoading(true);

    try {
      const res = await fetch(`${process.env.NEXT_PUBLIC_API_ENDPOINT}/tests`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(linePoints),
      })

      const evaluation = await res.json()
      setEvaluations(evaluations.concat([evaluation]));
      setEvaluated(true);

      window.scrollTo({
        top: 0,
        behavior: 'smooth'
      });
    } finally {
      setLoading(false);
    }
  }

  const handleAddPerson = () => {
    setEvaluated(false);
  }

  return (
    <>
      {!evaluated ? <PersonalityTestForm onFinish={handleFinish}/>
        : <PersonalityTestReport evaluations={evaluations} onAddPerson={handleAddPerson}/>}
      <Spin spinning={loading} fullscreen />
    </>
  );
}

export interface IEvaluation {
  extraversionValue: number;
  neuroticismValue: number;
  conscientiousnessValue: number;
  agreeablenessValue: number;
  opennessValue: number;
  commentary: string;
}
