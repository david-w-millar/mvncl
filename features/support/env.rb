require 'aruba/cucumber'

Before do

  @dirs = ['build/install/mvncl/bin']

  # Time and tide wait for no man.
  # Neither does testing when you're on a train
  # I frequently test over cellular networks...
  @aruba_timeout_seconds = 15

end

