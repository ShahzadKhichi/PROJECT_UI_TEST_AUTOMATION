#!/usr/bin/env python3
"""Direct Pynguin execution script."""
import subprocess
import sys
import os

def run_pynguin():
    """Run Pynguin to generate tests."""
    cmd = [
        sys.executable, "-m", "pynguin",
        "--project-path", ".",
        "--output-path", "tests",
        "--module-name", "my_code",
        "--algorithm", "RANDOM",
        "--maximum-search-time", "5"
    ]
    
    print("Running:", " ".join(cmd))
    result = subprocess.run(cmd, capture_output=True, text=True)
    
    print("STDOUT:", result.stdout)
    print("STDERR:", result.stderr)
    print("Return code:", result.returncode)
    
    return result.returncode

if __name__ == "__main__":
    # Ensure my_code.py exists
    if not os.path.exists("my_code.py"):
        print("Creating my_code.py...")
        with open("my_code.py", "w") as f:
            f.write("""
def add(a, b):
    return a + b

def subtract(a, b):
    return a - b

def is_even(n):
    return n % 2 == 0
""")
    
    # Run Pynguin
    ret = run_pynguin()
    
    # Check results
    if os.path.exists("tests") and os.listdir("tests"):
        print("✅ Tests generated successfully!")
        for file in os.listdir("tests"):
            if file.endswith(".py"):
                print(f"\n--- {file} ---")
                with open(os.path.join("tests", file), "r") as f:
                    print(f.read()[:500])  # Show first 500 chars
    else:
        print("❌ No tests generated")
