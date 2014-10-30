require 'aruba/cucumber'

Before do
  @dirs = ['build/install/mvncl/bin']
  # I test frequently over celular networks...
  @aruba_timeout_seconds = 15
end

