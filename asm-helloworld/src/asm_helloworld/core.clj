(ns asm-helloworld.core
  (:gen-class)
  (:import
   [java.lang.reflect Method]
   [org.objectweb.asm ClassWriter MethodVisitor Opcodes]))


(defn gen-helloworld-class []
  (let [cw (new org.objectweb.asm.ClassWriter 0)
        clazz (.visit cw Opcodes/V1_6
                      (+ Opcodes/ACC_PUBLIC Opcodes/ACC_SUPER)
                      "example/HelloWorld"
                      nil
                      "java/lang/Object"
                      nil)]
    ;; default constructor
    (let [mv (.visitMethod cw Opcodes/ACC_PUBLIC "<init>" "()V" nil nil)]
      (doto mv
        (.visitCode)
        (.visitVarInsn Opcodes/ALOAD 0)
        (.visitMethodInsn Opcodes/INVOKESPECIAL "java/lang/Object" "<init>" "()V")
        (.visitInsn Opcodes/RETURN)
        (.visitMaxs 1 1)
        (.visitEnd)
        )
      )

    ;; method
    (let [mv (.visitMethod cw (+ Opcodes/ACC_PUBLIC Opcodes/ACC_STATIC) "hello" "()V" nil nil)]
      (doto mv
        (.visitCode)
        (.visitFieldInsn Opcodes/GETSTATIC "java/lang/System" "out" "Ljava/io/PrintStream;")
        (.visitLdcInsn "HelloWorld")
        (.visitMethodInsn Opcodes/INVOKEVIRTUAL "java/io/PrintStream" "println" "(Ljava/lang/String;)V")
        (.visitInsn Opcodes/RETURN)
        (.visitMaxs 2 1)
        (.visitEnd)
        )
      )
    (.visitEnd cw)
    (.toByteArray cw)))

(defn try-load-helloworld-class [name bytes]
  (let [clazz (atom nil)
        cls (ClassLoader/getSystemClassLoader)
        method (.getDeclaredMethod java.lang.ClassLoader "defineClass" (into-array [String (Class/forName "[B") (Integer/TYPE) (Integer/TYPE)]))]
    (try
      (.setAccessible method true)
      (reset! clazz
              (.invoke method cls (into-array Object [name bytes (int 0) (int (count bytes))])))
      (finally
        (.setAccessible method false)))
    @clazz))

(defn get-helloworld-class []
  (try
    (Class/forName "example.HelloWorld")
    (catch Exception e
      (try-load-helloworld-class "example.HelloWorld" (gen-helloworld-class)))))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (let [clazz (get-helloworld-class)
        methods (.getMethods clazz)
        s (seq methods)
        method (first s)]
    (.invoke method nil nil))
  (println "Done"))
