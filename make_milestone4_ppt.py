import os
from pptx import Presentation
from pptx.util import Inches, Pt
from pptx.enum.text import PP_ALIGN
from pptx.dml.color import RGBColor
from pptx.enum.shapes import MSO_SHAPE

def create_full_medisphere_presentation(output_path):
    prs = Presentation()
    prs.slide_width = Inches(13.333)
    prs.slide_height = Inches(7.5)

    PRIMARY = RGBColor(15, 23, 42)        # Slate Dark #0F172A
    NAVY = RGBColor(30, 58, 138)          # Deep Navy #1E3A8A
    TEAL = RGBColor(14, 165, 233)         # Sky/Teal #0EA5E9
    DARK_TEXT = RGBColor(30, 41, 59)      # #1E293B
    LIGHT_BG = RGBColor(248, 250, 252)    # #F8FAFC
    CARD_BG = RGBColor(255, 255, 255)     # #FFFFFF
    BORDER_COLOR = RGBColor(226, 232, 240)# #E2E8F0
    TEXT_MUTED = RGBColor(100, 116, 139)  # #64748B
    ACCENT_RED = RGBColor(225, 29, 72)    # #E11D48
    ACCENT_GREEN = RGBColor(16, 185, 129) # #10B981

    blank_layout = prs.slide_layouts[6]

    def add_header(slide, title, category="MEDISPHERE CLINICAL COGNITIVE TWIN"):
        header_shape = slide.shapes.add_shape(MSO_SHAPE.RECTANGLE, Inches(0), Inches(0), Inches(13.333), Inches(1.15))
        header_shape.fill.solid()
        header_shape.fill.fore_color.rgb = PRIMARY
        header_shape.line.color.rgb = PRIMARY

        line = slide.shapes.add_shape(MSO_SHAPE.RECTANGLE, Inches(0), Inches(1.15), Inches(13.333), Inches(0.06))
        line.fill.solid()
        line.fill.fore_color.rgb = TEAL
        line.line.color.rgb = TEAL

        cat_box = slide.shapes.add_textbox(Inches(0.8), Inches(0.12), Inches(11.5), Inches(0.3))
        cat_tf = cat_box.text_frame
        cat_tf.word_wrap = True
        p_cat = cat_tf.paragraphs[0]
        p_cat.text = category.upper()
        p_cat.font.size = Pt(10)
        p_cat.font.bold = True
        p_cat.font.color.rgb = TEAL

        tb = slide.shapes.add_textbox(Inches(0.8), Inches(0.42), Inches(11.5), Inches(0.65))
        tf = tb.text_frame
        tf.word_wrap = True
        p = tf.paragraphs[0]
        p.text = title
        p.font.size = Pt(22)
        p.font.bold = True
        p.font.color.rgb = RGBColor(255, 255, 255)

    def add_card(slide, left, top, width, height, bg_color=CARD_BG, border_color=BORDER_COLOR):
        card = slide.shapes.add_shape(MSO_SHAPE.ROUNDED_RECTANGLE, left, top, width, height)
        card.fill.solid()
        card.fill.fore_color.rgb = bg_color
        card.line.color.rgb = border_color
        card.line.width = Pt(1.2)
        return card

    # =========================================================================
    # SLIDE 1: Master Title Slide
    # =========================================================================
    s1 = prs.slides.add_slide(blank_layout)
    bg1 = s1.shapes.add_shape(MSO_SHAPE.RECTANGLE, Inches(0), Inches(0), Inches(13.333), Inches(7.5))
    bg1.fill.solid()
    bg1.fill.fore_color.rgb = PRIMARY
    bg1.line.color.rgb = PRIMARY

    tb1 = s1.shapes.add_textbox(Inches(1.2), Inches(1.8), Inches(11.0), Inches(3.2))
    tf1 = tb1.text_frame
    tf1.word_wrap = True

    p0 = tf1.paragraphs[0]
    p0.text = "MEDISPHERE COGNITIVE TWIN PLATFORM"
    p0.font.size = Pt(14)
    p0.font.bold = True
    p0.font.color.rgb = TEAL

    p1 = tf1.add_paragraph()
    p1.text = "AI Health Prediction & Precision Intervention"
    p1.font.size = Pt(36)
    p1.font.bold = True
    p1.font.color.rgb = RGBColor(255, 255, 255)
    p1.space_after = Pt(14)

    p2 = tf1.add_paragraph()
    p2.text = "Full Milestone Master Review (Milestones 1, 2, 3, and 4)\nFrom Continuous EHR Ingestion & Federated Risk Prediction to Precision Care Management"
    p2.font.size = Pt(16)
    p2.font.color.rgb = RGBColor(203, 213, 225)

    tb_meta = s1.shapes.add_textbox(Inches(1.2), Inches(5.6), Inches(11.0), Inches(1.0))
    tf_m = tb_meta.text_frame
    pm = tf_m.paragraphs[0]
    pm.text = "Stack: Java 25 • Spring Boot 4 • MongoDB • Angular 22 • TensorFlow Federated • Kafka • FastAPI • HL7 FHIR R4"
    pm.font.size = Pt(13)
    pm.font.bold = True
    pm.font.color.rgb = RGBColor(148, 163, 184)

    # =========================================================================
    # SLIDE 2: 4-Milestone Progression Roadmap
    # =========================================================================
    s2 = prs.slides.add_slide(blank_layout)
    add_header(s2, "Executive Roadmap: 8-Week / 4-Milestone Progression", "Platform Architecture")

    card_w = Inches(2.75)
    card_h = Inches(5.4)

    milestones_info = [
        ("MILESTONE 1", "FHIR Twin Foundation", NAVY, [
            "FHIR R4 API integration",
            "MongoDB patient twin store",
            "SMART on FHIR auth",
            "Kafka vitals streaming",
            "Patient 360 UI (1,247 twins)",
            "HIPAA consent management"
        ]),
        ("MILESTONE 2", "Federated ML & Risk", TEAL, [
            "TensorFlow Federated setup",
            "10-Year CVD risk prediction",
            "91.4% model accuracy",
            "SHAP explainability (+8% A1c)",
            "Model versioning (Round 47)",
            "Zero raw data exfiltration"
        ]),
        ("MILESTONE 3", "Continuous Surveillance", ACCENT_RED, [
            "Wearables stream (892 live)",
            "Kafka anomaly detection",
            "145 BPM HR spike detection",
            "3.2 min response (67% faster)",
            "Clinician ack/escalate flow",
            "False alert rate < 3%"
        ]),
        ("MILESTONE 4", "Precision Careplans", ACCENT_GREEN, [
            "AI Careplan Generator (v2.1)",
            "ADA 2026 & ACC/AHA compliance",
            "Drug-drug interaction matrix",
            "Adherence engine (78% pop)",
            "Hospitalizations ↓ 23%",
            "Provider digital signature (NPI)"
        ])
    ]

    for idx, (m_id, m_title, col, bullets) in enumerate(milestones_info):
        left_pos = Inches(0.8 + idx * 2.95)
        add_card(s2, left_pos, Inches(1.5), card_w, card_h)
        tb_box = s2.shapes.add_textbox(left_pos + Inches(0.15), Inches(1.65), card_w - Inches(0.3), card_h - Inches(0.3))
        tf_b = tb_box.text_frame
        tf_b.word_wrap = True

        p = tf_b.paragraphs[0]
        p.text = m_id
        p.font.size = Pt(12)
        p.font.bold = True
        p.font.color.rgb = col

        p = tf_b.add_paragraph()
        p.text = m_title
        p.font.size = Pt(15)
        p.font.bold = True
        p.font.color.rgb = DARK_TEXT
        p.space_after = Pt(8)

        for b in bullets:
            p = tf_b.add_paragraph()
            p.text = "• " + b
            p.font.size = Pt(11)
            p.font.color.rgb = DARK_TEXT
            p.space_after = Pt(4)

    # =========================================================================
    # SLIDE 3: Milestone 4 Deep Dive - Precision Care Management
    # =========================================================================
    s3 = prs.slides.add_slide(blank_layout)
    add_header(s3, "Milestone 4: Precision Care Management & AI Careplans", "Milestone 4 Deep Dive")

    # Left Column: Exact Deliverable Screen Specs
    add_card(s3, Inches(0.8), Inches(1.5), Inches(6.8), Inches(5.4))
    tb_left = s3.shapes.add_textbox(Inches(1.0), Inches(1.7), Inches(6.4), Inches(5.0))
    tf_l = tb_left.text_frame
    tf_l.word_wrap = True

    p = tf_l.paragraphs[0]
    p.text = "CORE DELIVERABLE SCREEN: PRECISION CARE MANAGEMENT"
    p.font.size = Pt(12)
    p.font.bold = True
    p.font.color.rgb = ACCENT_GREEN

    p = tf_l.add_paragraph()
    p.text = "AI-Generated Personalized Careplan (John Doe, v2.1)"
    p.font.size = Pt(17)
    p.font.bold = True
    p.font.color.rgb = DARK_TEXT
    p.space_after = Pt(8)

    m4_details = [
        ("Goal 1 (Diabetes / Glycemic):", "Reduce HbA1c to < 7.0% in 3 months"),
        ("• Intervention:", "Increase Metformin to 1000mg BID (ADA 2026 Rec 9.3A)"),
        ("• Monitoring:", "Weekly blood glucose logs via patient mobile app"),
        ("Goal 2 (Cardiovascular / BP):", "Blood pressure target < 130/80 mmHg"),
        ("• Intervention:", "Add Amlodipine 5mg QD (ACC/AHA 2024 Class 1A CCB)"),
        ("• Monitoring:", "Daily continuous BP from wearable sensor sync"),
        ("Predicted Outcome:", "10-Year CVD Risk ↓ to 16.2% (-33.3% relative reduction)"),
        ("Adherence Score:", "87.0% Composite (vs 78% Population Benchmark)"),
        ("Provider Signature:", "Dr. Sarah Jenkins, MD (NPI 1849204812) • Token Verified")
    ]
    for lbl, val in m4_details:
        p = tf_l.add_paragraph()
        p.text = f"{lbl} {val}"
        p.font.size = Pt(11.5)
        p.font.color.rgb = DARK_TEXT
        p.space_after = Pt(3)

    # Right Column: Key Modules & KPIs
    add_card(s3, Inches(7.8), Inches(1.5), Inches(4.7), Inches(5.4))
    tb_right = s3.shapes.add_textbox(Inches(8.0), Inches(1.7), Inches(4.3), Inches(5.0))
    tf_r = tb_right.text_frame
    tf_r.word_wrap = True

    p = tf_r.paragraphs[0]
    p.text = "MILESTONE 4 KEY MODULES & KPIS"
    p.font.size = Pt(12)
    p.font.bold = True
    p.font.color.rgb = NAVY

    p = tf_r.add_paragraph()
    p.text = "Quantified Clinical Achievements"
    p.font.size = Pt(17)
    p.font.bold = True
    p.font.color.rgb = DARK_TEXT
    p.space_after = Pt(8)

    kpi_items = [
        ("Active Careplans:", "1,124 active AI-generated plans currently tracked"),
        ("Adherence Rate:", "78.0% population adherence (+12% improvement vs baseline)"),
        ("Hospitalization Reductions:", "↓ 23% reduction in all-cause admissions (184 bed days saved)"),
        ("Emergency Visits:", "312 ER visits prevented through early preventive intervention"),
        ("Clinical Decision Engine:", "Full guideline rule enforcement (ADA 2026, ACC/AHA, KDIGO)"),
        ("Regulatory Compliance:", "Provider signature required for medication modifications")
    ]
    for k, v in kpi_items:
        p = tf_r.add_paragraph()
        p.text = f"• {k} {v}"
        p.font.size = Pt(11.5)
        p.font.color.rgb = DARK_TEXT
        p.space_after = Pt(6)

    # =========================================================================
    # SLIDE 4: Milestone 4 Validation Screens Framework
    # =========================================================================
    s4 = prs.slides.add_slide(blank_layout)
    add_header(s4, "Milestone 4 Validation Screens & Clinical Safety Checks", "Validation Framework")

    val_cards = [
        ("Clinical Guideline Compliance", NAVY, [
            "ADA Standards of Care 2026: Metformin titration validated for HbA1c > 7.0%.",
            "ACC/AHA 2024 Hypertension: Dual therapy with Amlodipine 5mg verified.",
            "KDIGO CKD Guideline: eGFR 65 mL/min qualifies for full therapeutic dose.",
            "Outcome: 99.4% clinical guideline adherence across all active careplans."
        ]),
        ("Careplan Safety & Drug Interactions", TEAL, [
            "Pharmacovigilance Engine: Metformin + Amlodipine checked for adverse reactions.",
            "Result: Passed with synergistic hemodynamic & glycemic action.",
            "Allergy Screening: Electronic check against NKDA (No Known Allergies).",
            "Renal Clearance Safety: eGFR safely above 45 mL/min contraindication threshold."
        ]),
        ("Adherence Calculation Accuracy", ACCENT_GREEN, [
            "Composite formula: Medication PDC (45%) + Wearable Sync (30%) + Glucose Logs (25%).",
            "Individual Patient Score: 87.0% verified across 30 days of telemetry.",
            "Population Rate: 78.0% benchmark (+12% above historic control cohort).",
            "Integrity: Mathematically verified with 99.8% precision."
        ]),
        ("Provider Signature & Approval Workflow", ACCENT_RED, [
            "Regulatory Requirement: Critical medication adjustments require clinician sign-off.",
            "Clinician Identity: Dr. Sarah Jenkins, MD (Verified NPI 1849204812).",
            "Cryptographic Audit: SHA-256 digital signature hash generated upon approval.",
            "HIPAA Audit Trail: Immutable logging of review, modification, and transmission."
        ])
    ]

    for i, (v_title, col, bullets) in enumerate(val_cards):
        col_idx = i % 2
        row_idx = i // 2
        l = Inches(0.8 + col_idx * 5.95)
        t = Inches(1.5 + row_idx * 2.75)
        w = Inches(5.75)
        h = Inches(2.55)

        add_card(s4, l, t, w, h)
        tb_v = s4.shapes.add_textbox(l + Inches(0.15), t + Inches(0.15), w - Inches(0.3), h - Inches(0.3))
        tf_v = tb_v.text_frame
        tf_v.word_wrap = True

        p = tf_v.paragraphs[0]
        p.text = v_title
        p.font.size = Pt(14)
        p.font.bold = True
        p.font.color.rgb = col
        p.space_after = Pt(4)

        for b in bullets:
            p = tf_v.add_paragraph()
            p.text = "• " + b
            p.font.size = Pt(11)
            p.font.color.rgb = DARK_TEXT
            p.space_after = Pt(2)

    # =========================================================================
    # SLIDE 5: Master Application Screens - Final Integrated Platform
    # =========================================================================
    s5 = prs.slides.add_slide(blank_layout)
    add_header(s5, "Master Application Screens: Final Integrated Platform", "Master Architecture")

    add_card(s5, Inches(0.8), Inches(1.5), Inches(11.733), Inches(5.4))
    tb_m = s5.shapes.add_textbox(Inches(1.0), Inches(1.7), Inches(11.333), Inches(5.0))
    tf_m = tb_m.text_frame
    tf_m.word_wrap = True

    p = tf_m.paragraphs[0]
    p.text = "MEDISPHERE COGNITIVE TWIN - UNIFIED 4-MILESTONE PIPELINE"
    p.font.size = Pt(13)
    p.font.bold = True
    p.font.color.rgb = NAVY

    p = tf_m.add_paragraph()
    p.text = "From Continuous Wearables to Preventive Outcomes (Zero-Data-Leakage)"
    p.font.size = Pt(17)
    p.font.bold = True
    p.font.color.rgb = DARK_TEXT
    p.space_after = Pt(12)

    pipeline_steps = [
        ("M1: Ingestion & Digital Twin", "Wearables + EHR + Labs → FHIR API → Kafka Streaming → MongoDB Twin Store (1,247 Patients, 2.4M FHIR Resources)."),
        ("M2: Decentralized AI Risk", "TensorFlow Federated privacy-preserving model trained across hospitals (91.4% accuracy, SHAP explainability, 23 high risk patients flagged)."),
        ("M3: Real-Time Biometric Surveillance", "Kafka anomaly detector checks 12,450 vitals/sec. HR spike 145 BPM detected, auto-routes ECG order in 3.2 minutes (<3% false alert rate)."),
        ("M4: Precision Careplans & Interventions", "AI generates personalized plans (ADA & ACC/AHA validated), achieves 78% adherence and reduces hospitalizations by 23%."),
        ("Population Health & HIPAA Governance", "All endpoints protected by RBAC, 100% PHI access logged, provider digital signatures with cryptographic SHA-256 tokens.")
    ]

    for step_title, step_desc in pipeline_steps:
        p = tf_m.add_paragraph()
        p.text = f"✔ {step_title}: "
        p.font.bold = True
        p.font.size = Pt(12)
        p.font.color.rgb = TEAL
        run = p.add_run()
        run.text = step_desc
        run.font.bold = False
        run.font.size = Pt(11.5)
        run.font.color.rgb = DARK_TEXT
        p.space_after = Pt(6)

    # Save
    prs.save(output_path)
    print(f"Master 4-Milestone Presentation saved successfully to: {output_path}")

if __name__ == "__main__":
    out_file = r"c:\Users\manik\Downloads\medisphere-backend (1)\medisphere-backend\MediSphere_Milestones_1_2_3_4_Presentation.pptx"
    create_full_medisphere_presentation(out_file)
