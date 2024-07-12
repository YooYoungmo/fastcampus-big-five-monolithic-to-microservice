package dtos

type LinePoints struct {
  LinePoint1  uint `json:"linePoint1"`
  LinePoint2  uint `json:"linePoint2"`
  LinePoint3  uint `json:"linePoint3"`
  LinePoint4  uint `json:"linePoint4"`
  LinePoint5  uint `json:"linePoint5"`
  LinePoint6  uint `json:"linePoint6"`
  LinePoint7  uint `json:"linePoint7"`
  LinePoint8  uint `json:"linePoint8"`
  LinePoint9  uint `json:"linePoint9"`
  LinePoint10 uint `json:"linePoint10"`
  LinePoint11 uint `json:"linePoint11"`
  LinePoint12 uint `json:"linePoint12"`
}

type EvaluationResult struct {
  Id                     uint   `json:"id"`
  ExtraversionValue      uint   `json:"extraversionValue"`
  Extraversion           string `json:"extraversion"`
  NeuroticismValue       uint   `json:"neuroticismValue"`
  Neuroticism            string `json:"neuroticism"`
  ConscientiousnessValue uint   `json:"conscientiousnessValue"`
  Conscientiousness      string `json:"conscientiousness"`
  AgreeablenessValue     uint   `json:"agreeablenessValue"`
  Agreeableness          string `json:"agreeableness"`
  OpennessValue          uint   `json:"opennessValue"`
  Openness               string `json:"openness"`
}
