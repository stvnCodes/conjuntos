# Genera el PDF de entrega. Uso: python3 documentacion/fuente/generar.py
import html
import pathlib
import subprocess
import tempfile

RAIZ = pathlib.Path(__file__).resolve().parents[2]
CHROME = "/Applications/Google Chrome.app/Contents/MacOS/Google Chrome"
INTEGRANTES = [
    "Juan Estiven Posada Rua",
    "Carlos Eduardo Hernández Munévar",
    "Andrés Santiago Bedoya Yepes",
]
UNIVERSIDAD = "Politécnico Colombiano Jaime Isaza Cadavid"

pagina = (pathlib.Path(__file__).parent / "documento.html").read_text()
pagina = pagina.replace("{{UNIVERSIDAD}}", html.escape(UNIVERSIDAD))
# Un integrante por línea en la portada
pagina = pagina.replace("{{INTEGRANTES}}", "<br>".join(html.escape(nombre) for nombre in INTEGRANTES))

with tempfile.TemporaryDirectory() as carpeta:
    origen = pathlib.Path(carpeta) / "documento.html"
    origen.write_text(pagina)
    subprocess.run([CHROME, "--headless", "--disable-gpu", "--no-pdf-header-footer",
                    f"--user-data-dir={carpeta}/perfil",
                    f"--print-to-pdf={RAIZ}/documentacion/Practica-Conjuntos.pdf",
                    origen.as_uri()], check=True, stderr=subprocess.DEVNULL, timeout=90)
